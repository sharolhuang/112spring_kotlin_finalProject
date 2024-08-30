package com.example.termproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

data class Cell(val row: Int, val col: Int) {
    var color: Char = 'R'
    var number: Int = 1
    init {
        reset()
    }
    fun reset()  {
        color = "RBY".random()
        number = 1
    }
    override fun toString(): String {
        return "$number"
    }
}

class Game(val activity: MainActivity) {
    private val grid = (0..5).map{ row -> // generate 6 rows of cells
        (0..5).map{ col -> // generate 6 columns of cells
            Cell(row, col)
        }
    }
    var score = 0
    val playable: Boolean  // it is your task to implement a computed property "playable"
        get() {
            for (row in 0..5) {
                for (col in 0..5) {
                    if (piece(row, col).size >= 3) {
                        return true
                    }
                }
            }
            return false
        }

    init {
        init()
    }
    fun show() {
        for (idx in 0..35) {
            val r = idx / 6
            val c = idx % 6
            activity.button[idx].text = grid[r][c].toString()
            val color = when (grid[r][c].color) {
                'R' -> Color.parseColor("#EF4444")
                'Y' -> Color.parseColor("#EAB308")
                'B' -> Color.parseColor("#3B82F6")
                'G' -> Color.parseColor("#64748B")
                else -> Color.GREEN
            }
            activity.button[idx].setBackgroundColor(color)
        }
        activity.updateScore(score)
    }
    fun init() {
        score = 0
        for (row in grid) {
            for (cell in row) {
                cell.reset()
            }
        }
    }
    fun restart() {
        init()
        show()
    }
    // the piece of same color of cell on (row, col)
    fun piece(row: Int, col: Int): List<Cell> {
        val ret = mutableSetOf(grid[row][col])
        val target = grid[row][col].color
        while (true) {
            val new = mutableListOf<Cell>()
            for (cell in ret) {
                if (cell.row > 0) { // cell is not in the top row
                    val upper = grid[cell.row - 1][cell.col]
                    if (upper.color == target && upper !in new)
                        new.add(upper)
                }
                if (cell.col > 0) { // cell is not in the leftest column
                    val left = grid[cell.row][cell.col - 1]
                    if (left.color == target && left !in new)
                        new.add(left)
                }
                if (cell.row < 5) { // cell is not in the bottom row
                    val lower = grid[cell.row + 1][cell.col]
                    if (lower.color == target && lower !in new)
                        new.add(lower)
                }
                if (cell.col < 5) { // cell is not in the rightest column
                    val right = grid[cell.row][cell.col + 1]
                    if (right.color == target && right !in new)
                        new.add(right)
                }
            }
            if (ret.containsAll(new)) break
            ret.addAll(new)
        }
        return ret.toList()
    }
    fun fall() {
        for (col in 0..5) {
            for (row in 5 downTo 1) {
                while (grid[row][col].color == ' ') {
                    for (r in (row - 1) downTo 0) {
                        grid[r + 1][col].color = grid[r][col].color
                        grid[r + 1][col].number = grid[r][col].number
                    }
                    grid[0][col].reset()
                }
            }
            if (grid[0][col].color == ' ') grid[0][col].reset()
        }
    }
    fun push(row: Int, col: Int) {
        println("button on row $row and column $col is pressed")
        val connected = piece(row, col)

        println("The size of the piece is ${connected.size}.")
        for (cell in connected) {
            println("${cell.row} ${cell.col} $cell")
        }

        // if size >= 3 amd color is not gray, then merge into grid[row][col]
        when {
            connected.size >= 3 && grid[row][col].color != 'G' -> {
                val total = connected.map { it.number }.sum()
                score += total - connected.map { it.number }.max()
                for (cell in connected) {
                    if (cell != grid[row][col]) {
                        cell.color = ' '
                        cell.number = 0
                    } else {
                        cell.number = minOf(total, 10)
                        if (cell.number >= 10) {
                            cell.color = 'G'
                        }
                    }
                }
                fall()
            }
            connected.size >= 3 && grid[row][col].color == 'G' -> {
                // it is your task
                score += 10 * connected.size
                for (cell in connected) {
                    cell.color = ' '
                    cell.number = 0
                }
                fall()
            }

            else -> {
                AlertDialog.Builder(activity)
                    .setMessage("You cannot push here").show()
            }
        }
        // 檢查是否還有可玩的方塊組合
        if (!playable) {
            AlertDialog.Builder(activity)
                .setMessage("Game Over! Your score is $score")
                .setPositiveButton("OK") { _, _ -> }
                .show()
        }
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var button: MutableList<Button>
    lateinit var scoreTextView: TextView
    val game = Game(this)
    private fun push(idx: Int) {
        game.push(idx / 6, idx % 6)
        game.show()
    }
    private fun reset() {
        game.restart()
        AlertDialog.Builder(this).setMessage("Reset").show()
    }
    fun updateScore(newScore: Int) {
        scoreTextView.text = "Score: $newScore"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scoreTextView = findViewById<TextView>(R.id.score)
        button = mutableListOf<Button>().apply {
            add(findViewById<Button>(R.id.button1))
            add(findViewById<Button>(R.id.button2))
            add(findViewById<Button>(R.id.button3))
            add(findViewById<Button>(R.id.button4))
            add(findViewById<Button>(R.id.button5))
            add(findViewById<Button>(R.id.button6))
            add(findViewById<Button>(R.id.button7))
            add(findViewById<Button>(R.id.button8))
            add(findViewById<Button>(R.id.button9))
            add(findViewById<Button>(R.id.button10))
            add(findViewById<Button>(R.id.button11))
            add(findViewById<Button>(R.id.button12))
            add(findViewById<Button>(R.id.button13))
            add(findViewById<Button>(R.id.button14))
            add(findViewById<Button>(R.id.button15))
            add(findViewById<Button>(R.id.button16))
            add(findViewById<Button>(R.id.button17))
            add(findViewById<Button>(R.id.button18))
            add(findViewById<Button>(R.id.button19))
            add(findViewById<Button>(R.id.button20))
            add(findViewById<Button>(R.id.button21))
            add(findViewById<Button>(R.id.button22))
            add(findViewById<Button>(R.id.button23))
            add(findViewById<Button>(R.id.button24))
            add(findViewById<Button>(R.id.button25))
            add(findViewById<Button>(R.id.button26))
            add(findViewById<Button>(R.id.button27))
            add(findViewById<Button>(R.id.button28))
            add(findViewById<Button>(R.id.button29))
            add(findViewById<Button>(R.id.button30))
            add(findViewById<Button>(R.id.button31))
            add(findViewById<Button>(R.id.button32))
            add(findViewById<Button>(R.id.button33))
            add(findViewById<Button>(R.id.button34))
            add(findViewById<Button>(R.id.button35))
            add(findViewById<Button>(R.id.button36))
        }
        for((i, btn) in button.withIndex()) {
            btn.setOnClickListener { push(i) }
        }
        findViewById<Button>(R.id.reset).setOnClickListener { reset() }
        game.show()
    }
}
# Kotlin Final Project, 2024 Spring
It's a final game project of Kotlin Programming course in NYCU
## Game Play
1. 在遊戲開始時，畫面上隨機產生6*6的方格，數字皆為1，顏色為以下三種隨機
    * <div style="display: inline-block; width:12px; height:12px; background-color: #EF4444;"></div> #EF4444
    * <div style="display: inline-block; width:12px; height:12px; background-color: #EAB308;"></div> #EAB308
    * <div style="display: inline-block; width:12px; height:12px; background-color: #3B82F6;"></div> #3B82F6
2. 當有三個同顏色的方塊連在一起的時候可以合併數字，然後上面的方塊將會往下遞補，而遞補後空白的方格將會隨機重新產生方格
    * 例如
        1. 有三個方格皆為1，會合併為一個數字3的格子，而另外兩個會消失，上方的方格會往下遞補
        2. 有三個方格分別為1 2 2，會合併成5
        3. 有四個方格分別為1 2 3 3，會合併成9
3. 合併後的方格數字最大為10，若該格子數字已達到10，將會變色為 <div style="display: inline-block; width:12px; height:12px; background-color: #64748B;"></div> #64748B，此時當有三個以上數字為10的方格被點擊，將會將這些方格都消除。
4. 當點擊的格子沒有連續三個同顏色連在一起時，跳出訊息通知使用者不能點
5. 當遊戲中沒有連續三個同顏色的格子連在一起時，跳出彈跳視窗通知使用者遊戲結束
6. 遊戲分數的計算方式
    * 合併方格時，用合併後的數字減去該區域中最大的數字為加分
        * 舉例來說:
            1. 合併三格 1 1 1 => 3，則加2分，因為3-1=2
            2. 合併四格 1 1 1 1 => 4，加3分，4-1=2
            3. 合併四格 1 2 3 4 => 10，加6分，10-4=6
            4. 合併三格 2 2 3 => 7，加4分，7-3=4
            5. 合併三格 5 7 7 => 10，加3分，10-7=3
    * 消除方格時，消除幾個就加 10 * 個數
        * 舉例來說:
            1. 消除 10 10 10，10*3=30，加30分
            2. 消除 5 個 10 則加 50分
7. 提供一個重新開始的按鈕，點擊後遊戲將會重新開始，重新產生方格，並可以正常遊戲。

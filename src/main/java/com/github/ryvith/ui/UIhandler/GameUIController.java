package com.github.ryvith.ui.UIhandler;

import com.github.ryvith.game.*;
import com.github.ryvith.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.awt.Point;
import java.net.URL;
import java.util.*;

public class GameUIController {
    // ====== UI组件注入 ======
    @FXML private GridPane boardGrid;
    @FXML private Label currentPlayerLabel;
    @FXML private VBox gameInfoBox;
    @FXML private ListView gameListView;
    @FXML private Label roundLabel; // 用于Gomoku的回合显示
    @FXML private HBox scoreBox;    // 用于reversi分数显示
    @FXML private Button passButton;
    @FXML private Button quitButton;

    // ===== 游戏逻辑相关 ======
    private GameManager gameManager;
    private Game currentGame;

    public void initialize(URL location, ResourceBundle resources) {
        initializeGame();
        initializeBoard();
        updateUI();


    }

    /**
     * 初始化游戏管理器
     */
    public void initializeGame(){
        try {
            // 创建游戏配置
            GameConfig config = new GameConfig.GameBuilder()
                    .withSize(8)
                    .withPlayers("Tom", "Jerry")
                    .build();

            this.gameManager = new GameManager(new ArrayList<>(List.of(
                    new Peace(config),
                    new Reversi(config),
                    new Gomoku(config)
            )));

            this.currentGame = gameManager.getCurrentGame();

            gameListView.setOnMouseClicked(event -> {
                int selectedIndex = gameListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    handleSwitchGame(selectedIndex);
                }
            });
        } catch (Exception e) {
            System.err.println("游戏初始化失败:");
            e.printStackTrace();
            throw e;
        }

    }


    /**
     * 初始化棋盘UI
     */
    public void initializeBoard() {
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();

        int size = currentGame.getBoard().getSize();

        // 添加列标签 (A-H)
        for (char c = 'A'; c < 'A' + size; c++) {
            Label label = new Label(String.valueOf(c));
            boardGrid.add(label, c - 'A' + 1, 0);
        }

        // 添加行标签 (1-8)
        for (int i = 1; i <= size; i++) {
            Label label = new Label(String.valueOf(i));
            boardGrid.add(label, 0, i);
        }

        // 创建棋盘格子
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane cell = createCell(row, col);
                cell.setUserData(new Point(row, col)); // 标记棋盘位置
                boardGrid.add(cell, col + 1, row + 1);
            }
        }
    }

    /**
     * 创建单个棋盘格子
     */
    private StackPane createCell(int row, int col) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("board-cell");
        cell.setPrefSize(60, 60);

        // 背景
        Rectangle bg = new Rectangle(60, 60, Color.BURLYWOOD);
        bg.setStroke(Color.BLACK);
        cell.getChildren().add(bg);

        // 棋子
        updateCellAppearance(cell, row, col);

        // 点击事件
        cell.setOnMouseClicked(e -> handleMove(row, col));

        return cell;
    }

    /**
     * 更新单个棋子外观
     */
    private void updateCellAppearance(StackPane cell, int row, int col) {
        // 保留背景，清除其他内容
        if (cell.getChildren().size() > 1) {
            cell.getChildren().remove(1, cell.getChildren().size());
        }

        Piece piece = currentGame.getBoard().getPiece(new Point(row, col));

        // 根据棋子类型添加相应图形
        switch (piece) {
            case BLACK:
                cell.getChildren().add(createPieceView(Color.BLACK));
                break;
            case WHITE:
                cell.getChildren().add(createPieceView(Color.WHITE));
                break;
            case VALID: // Reversi的合法落子提示
                Circle hint = new Circle(8, Color.rgb(0, 255, 0, 0.3));
                cell.getChildren().add(hint);
                break;
        }
    }

    /**
     * 创建棋子视图
     */
    private Circle createPieceView(Color color) {
        Circle piece = new Circle(15, color);
        if(color == Color.WHITE){
            piece.setStroke(Color.BLACK);
        }
        return piece;
    }

    // =============== 更新UI ===============

    public void updateUI(){
        updateBoard();
        updateGameInfo();
        updateGameList();
        updateControlButtons();

        // 游戏特定UI更新
        if(currentGame instanceof Reversi){
            updateReversiSpecificUI();
        }
        if(currentGame instanceof Gomoku){
            updateGomokuSpecificUI();
        }
        else{
            // Peace隐藏不相关元素
            scoreBox.setVisible(false);
            roundLabel.setVisible(false);
        }
    }

    /**
     * 更新棋盘显示
     */
    private void updateBoard() {
        // 清除所有现有棋子显示
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof StackPane && node.getUserData() instanceof Point) {
                StackPane cell = (StackPane) node;
                // 保留背景，移除其他元素
                if (cell.getChildren().size() > 1) {
                    cell.getChildren().remove(1, cell.getChildren().size());
                }
                // 重新渲染棋子
                Point pos = (Point) node.getUserData();
                updateCellAppearance(cell, pos.x, pos.y);
            }
        }
    }

    /* 更新中间游戏信息 */
    private void updateGameInfo() {
        // 清空旧内容
        gameInfoBox.getChildren().clear();

        // 添加基本信息
        int gameIndex = gameManager.getGames().indexOf(currentGame) + 1;
        Label title = new Label("Game " + gameIndex + ": " + getGameType(currentGame));
        title.getStyleClass().add("game-title");
        gameInfoBox.getChildren().add(title);


        // 添加玩家信息
        addPlayerInfo(currentGame.getPlayers()[0]);
        addPlayerInfo(currentGame.getPlayers()[1]);

    }

    /* 添加玩家信息 （得分，棋子） */
    private void addPlayerInfo(Player player) {
        HBox playerBox = new HBox(10);
        playerBox.getStyleClass().add("玩家信息");

        // 玩家棋子标识
        Circle piece = new Circle(8,
                player.piece() == Piece.BLACK ? Color.BLACK : Color.WHITE);
        if (player.piece() == Piece.WHITE) {
            piece.setStroke(Color.BLACK);
        }

        // 玩家名称和得分
        Label nameLabel = new Label(player.name());
        if (player.name().equals(currentGame.getCurrentPlayer().name()) &&
                player.piece() == currentGame.getCurrentPlayer().piece()) {
            nameLabel.getStyleClass().add("current-player");
        }

        playerBox.getChildren().addAll(piece, nameLabel);

        // Reversi显示得分
        if (currentGame instanceof Reversi reversi) {
            int score = reversi.countPieces(player.piece());
            Label scoreLabel = new Label("得分: " + score);
            playerBox.getChildren().add(scoreLabel);
        }

        gameInfoBox.getChildren().add(playerBox);
    }

    /**
     * 更新右侧游戏列表
     */
    private void updateGameList() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < gameManager.getGames().size(); i++) {
            Game game = gameManager.getGames().get(i);
            String text = (i + 1) + ". " + getGameType(game);
            if (game == currentGame) {
                text = "→ " + text;
            }
            items.add(text);
        }
        gameListView.setItems(items);
    }

    /**
     * 更新控制按钮状态
     */
    private void updateControlButtons() {
        if (passButton != null) {  // 添加null检查
            passButton.setDisable(!(currentGame instanceof Reversi) ||
                    !((Reversi)currentGame).canPass());
        }
    }

    /**
     * Reversi特定UI更新
     */
    private void updateReversiSpecificUI() {
        Reversi reversi = (Reversi) currentGame;

        // 显示分数
        scoreBox.getChildren().clear();
        int blackScore = reversi.countPieces(Piece.BLACK);
        int whiteScore = reversi.countPieces(Piece.WHITE);
        scoreBox.getChildren().addAll(
                createScoreLabel("黑方: " + blackScore, Color.BLACK),
                createScoreLabel("白方: " + whiteScore, Color.WHITE)
        );
        scoreBox.setVisible(true);

        // 显示合法落子位置
        if (!reversi.shouldGameEnd()) {
            Map<Point, List<Point>> validMoves = reversi.getValidPosition();
            clearAllValidMarks();
            for (Point move : validMoves.keySet()) {
                for (Node node : boardGrid.getChildren()) {
                    if (node instanceof StackPane && node.getUserData() instanceof Point) {
                        Point pos = (Point) node.getUserData();
                        if (pos.equals(move)) {
                            Circle hint = new Circle(8, Color.rgb(0, 255, 0, 0.3));
                            ((StackPane) node).getChildren().add(hint);
                            break;
                        }
                    }
                }
            }
        }
    }

    /* 清除所有有效位置标记 */
    private void clearAllValidMarks() {
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof StackPane) {
                StackPane cell = (StackPane) node;
                // 保留背景和棋子，只移除提示标记
                if (cell.getChildren().size() > 1) {
                    // 检查是否是提示标记（绿色圆圈）
                    for (int i = cell.getChildren().size() - 1; i >= 1; i--) {
                        Node child = cell.getChildren().get(i);
                        if (child instanceof Circle && ((Circle) child).getFill().equals(Color.rgb(0, 255, 0, 0.3))) {
                            cell.getChildren().remove(i);
                        }
                    }
                }
            }
        }
    }


    private Label createScoreLabel(String text, Color color) {
        Label label = new Label(text);
        label.setTextFill(color == Color.BLACK ? Color.WHITE : Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(
                color, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setPadding(new Insets(5));
        return label;
    }

    /**
     * Gomoku特定UI更新
     */
    private void updateGomokuSpecificUI() {
        Gomoku gomoku = (Gomoku) currentGame;
        roundLabel.setText("回合: " + gomoku.getRound());
        roundLabel.setVisible(true);
        scoreBox.setVisible(false);
    }


    // =============== 事件处理方法 ==============


    @FXML
    /* 处理落子事件 */
    private void handleMove(int x, int y) {
        Point point = new Point(x, y);
        if (currentGame.handleMove(point)) {
            updateUI();

            if (currentGame.shouldGameEnd()) {
                GameResult result = currentGame.handleGameEnd();
                showGameResult(result);
                disableBoard();
            }else{
                currentGame.switchPlayer();
                updateUI();
            }
        } else {
            showAlert("错误", "非法落子！");
        }
    }

    @FXML
    private void handleQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退出");
        alert.setHeaderText("确定要退出游戏吗？");

        // 只保留“是”和“否”按钮
        ButtonType yes = new ButtonType("是", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("否", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yes, no);

        alert.showAndWait().ifPresent(type -> {
            if (type == yes) {
                System.exit(0); // 直接退出程序
            }
        });
    }



    @FXML
    private void handlePass() {
        try {
            // 检查当前游戏是否为Reversi（只有Reversi需要pass功能）
            if (!(currentGame instanceof Reversi reversi)) {
                showAlert("无效操作", "当前游戏模式不支持Pass操作");
                return;
            }

            boolean gameShouldContinue = reversi.handlePass();
            if (!gameShouldContinue) {
                // 游戏结束（双方都无法落子）
                GameResult result = reversi.handleGameEnd();
                showGameResult(result);
            } else {
                // 更新UI显示
                updateUI();
                showAlert("回合跳过",
                        String.format("玩家 %s 跳过回合\n现在轮到 %s", reversi.getCurrentPlayer().name(),
                                reversi.getOpponent(reversi.getCurrentPlayer()).name()));
            }
        } catch (IllegalStateException e) {
            showAlert("不能跳过回合", e.getMessage());
        } catch (Exception e) {
            showAlert("错误", "执行Pass操作时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSwitchGame(int index) {
        if (index < 0 || index >= gameManager.getGames().size()) {
            showAlert("错误", "无效的游戏索引！");
            return;
        }

        Game newGame = gameManager.getGames().get(index);

        if (newGame == currentGame) {
            return; // 已是当前游戏，无需切换
        }

        this.currentGame = newGame;
        gameManager.setCurrentGame(newGame); // 确保 GameManager 也同步更新

        // 根据游戏类型调整UI显示
        if (currentGame instanceof Reversi) {
            scoreBox.setVisible(true);
            roundLabel.setVisible(false);
        } else if (currentGame instanceof Gomoku) {
            scoreBox.setVisible(false);
            roundLabel.setVisible(true);
        } else {
            scoreBox.setVisible(false);
            roundLabel.setVisible(false);
        }

        initializeBoard(); // 重新初始化棋盘
        updateUI();        // 更新所有相关UI
    }

    @FXML
    private void handleAddNewGame() {
        // 创建一个 Alert 弹窗用于选择
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("选择游戏类型");
        alert.setHeaderText("请选择要添加的新游戏类型：");
        alert.setContentText("请选择一种棋类：");

        ButtonType peaceButton = new ButtonType("Peace");
        ButtonType reversiButton = new ButtonType("Reversi");
        ButtonType gomokuButton = new ButtonType("Gomoku");
        ButtonType cancelButton = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(peaceButton, reversiButton, gomokuButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        Game newGame = null;

        if (result.isPresent()) {
            if (result.get() == peaceButton) {
                newGame = new Peace(currentGame.getConfig());
            } else if (result.get() == reversiButton) {
                newGame = new Reversi(currentGame.getConfig());
            } else if (result.get() == gomokuButton) {
                newGame = new Gomoku(currentGame.getConfig());
            }
            else {
                return; // 用户取消
            }

            // 添加到列表并切换为当前游戏
            if(newGame != null){
                gameManager.getGames().add(newGame);
                gameListView.getItems().add(newGame);
                gameListView.getSelectionModel().select(newGame);
                currentGame = newGame;
            }


            updateUI(); // 刷新 UI
        }
    }

    /* 展现游戏结束画面 */
    private void showGameResult(GameResult result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("游戏结束");
        alert.setHeaderText(null);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);

        Label gameTitle = new Label("游戏结束");
        gameTitle.getStyleClass().add("game-title");

        Label resultMessage = new Label();

        if(currentGame instanceof Peace) {
            resultMessage.setText("棋盘已满，游戏结束！");
        }
        else if (result != null) {  // 添加null检查
            if(currentGame instanceof Reversi) {
                String message = String.format("黑方: %d 分\n白方: %d 分\n",
                        result.blackScore(), result.whiteScore());
                message += (result.blackScore() == result.whiteScore()) ?
                        "比赛结果: 平局！" : "获胜者: " + result.winner().name();
                resultMessage.setText(message);
            }
            else if(currentGame instanceof Gomoku) {
                resultMessage.setText("恭喜 " + result.winner().name() + " 达成五子连线，获得胜利！");
            }
        }

        content.getChildren().addAll(gameTitle, resultMessage);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    // === 其他辅助方法 ===
    private String getGameType(Game currentGame) {
        if (currentGame instanceof Peace) return "Peace";
        if (currentGame instanceof Reversi) return "Reversi";
        if (currentGame instanceof Gomoku) return "Gomoku";
        return "Unknown";
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void disableBoard() {
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof StackPane) {
                node.setDisable(true);
            }
        }
    }

}

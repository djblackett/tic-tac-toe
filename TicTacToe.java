package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TicTacToe extends JFrame {
    Board board;
    java.util.List<Cell> cells;
    JLabel labelStatus;
    JButton buttonReset;
    JButton buttonPlayer1 = new JButton();
    JButton buttonPlayer2 = new JButton();
    boolean isGameOver = false;
    Cell a1, a2, a3, b1, b2, b3, c1, c2, c3;
    String inProgress = "The turn of %s Player (%s)";
    GameType gameType;
    enum GameType {
        HUMAN_VS_HUMAN,
        HUMAN_VS_ROBOT,
        ROBOT_VS_HUMAN,
        ROBOT_VS_ROBOT
    }


    static java.util.List<java.util.List<Cell>> winningCombinations;


    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setTitle("Tic Tac Toe");
        //setResizable(false);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        a1 = new Cell(" ");
        a2 = new Cell(" ");
        a3 = new Cell(" ");
        b1 = new Cell(" ");
        b2 = new Cell(" ");
        b3 = new Cell(" ");
        c1 = new Cell(" ");
        c2 = new Cell(" ");
        c3 = new Cell(" ");

        board = new Board();

        a1.setName("ButtonA1");
        a2.setName("ButtonA2");
        a3.setName("ButtonA3");
        b1.setName("ButtonB1");
        b2.setName("ButtonB2");
        b3.setName("ButtonB3");
        c1.setName("ButtonC1");
        c2.setName("ButtonC2");
        c3.setName("ButtonC3");

        cells = new ArrayList<>(Arrays.asList(a1, a2, a3, b1, b2, b3, c1, c2, c3));

        winningCombinations = new ArrayList<>();

        winningCombinations.add(Arrays.asList(a3, b3, c3));
        winningCombinations.add(Arrays.asList(a2, b2, c2));
        winningCombinations.add(Arrays.asList(a1, b1, c1));

        winningCombinations.add(Arrays.asList(a3, a2, a1));
        winningCombinations.add(Arrays.asList(b3, b2, b1));
        winningCombinations.add(Arrays.asList(c3, c2, c1));

        winningCombinations.add(Arrays.asList(a3, b2, c1));
        winningCombinations.add(Arrays.asList(a1, b2, c3));


        board.add(a3);
        board.add(b3);
        board.add(c3);

        board.add(a2);
        board.add(b2);
        board.add(c2);

        board.add(a1);
        board.add(b1);
        board.add(c1);

        Dimension buttonDimensions = new Dimension(100, 50);
        buttonReset = new JButton("Start");
        buttonReset.setName("ButtonStartReset");
        buttonReset.setPreferredSize(buttonDimensions);



        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new GridLayout(1, 3));
        topButtonPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JButton buttonPlayer1 = new JButton("Human");
        buttonPlayer1.setName("ButtonPlayer1");
        buttonPlayer1.setPreferredSize(buttonDimensions);

        JButton buttonPlayer2 = new JButton("Human");
        buttonPlayer2.setName("ButtonPlayer2");
        buttonPlayer2.setPreferredSize(buttonDimensions);


        JMenuBar menuBar = new JMenuBar();

        JMenu menuGame = new JMenu();
        menuGame.setName("MenuGame");
        menuGame.setText("Game");
        menuGame.setPreferredSize(new Dimension(50,30));

        JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
        menuHumanHuman.setName("MenuHumanHuman");

        JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
        menuHumanRobot.setName("MenuHumanRobot");

        JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
        menuRobotHuman.setName("MenuRobotHuman");

        JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
        menuRobotRobot.setName("MenuRobotRobot");

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");

        menuGame.add(menuHumanHuman);
        menuGame.add(menuHumanRobot);
        menuGame.add(menuRobotHuman);
        menuGame.add(menuRobotRobot);
        menuGame.addSeparator();
        menuGame.add(menuExit);

        menuBar.add(menuGame);


        menuHumanHuman.addActionListener(e -> {
            buttonPlayer1.setText("Human");
            buttonPlayer2.setText("Human");
            resetGame();
            gameType = GameType.HUMAN_VS_HUMAN;
            initializeGame();

            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);
            humanVsHuman();
            buttonReset.setText("Reset");
        });

        menuHumanRobot.addActionListener(e -> {
            buttonPlayer1.setText("Human");
            buttonPlayer2.setText("Robot");
            resetGame();
            gameType = GameType.HUMAN_VS_ROBOT;
            initializeGame();
            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);
            buttonReset.setText("Reset");
            humanVsComputer();

        });

        menuRobotHuman.addActionListener(e -> {
            buttonPlayer1.setText("Robot");
            buttonPlayer2.setText("Human");
            resetGame();
            gameType = GameType.ROBOT_VS_HUMAN;
            initializeGame();
            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);
            buttonReset.setText("Reset");
            chooseRandomCell();
            humanVsComputer();

        });

        menuRobotRobot.addActionListener(e -> {
            buttonPlayer1.setText("Robot");
            buttonPlayer2.setText("Robot");
            resetGame();
            gameType = GameType.ROBOT_VS_ROBOT;
            initializeGame();
            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);
            buttonReset.setText("Reset");
            computerVsComputer();
        });



        topButtonPanel.add(buttonPlayer1);
        topButtonPanel.add(buttonReset);
        topButtonPanel.add(buttonPlayer2);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(menuBar, gbc);


        gbc.gridx = 0;
        gbc.gridwidth = getWidth();
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(topButtonPanel, gbc);


        gbc.gridy = 2;


        gbc.weighty = 10;
        gbc.weightx = 10;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;

        add(board, gbc);


        labelStatus = new JLabel("Game is not started");
        labelStatus.setName("LabelStatus");
        labelStatus.setPreferredSize(new Dimension(100,50));
        //labelStatus.setFocusable(false);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 3;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 5, 3, 0);

        add(labelStatus, gbc);


        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 5, 5 );


        setVisible(true);

        buttonPlayer1.addActionListener(e -> {
            if (buttonPlayer1.getText().equals("Human")) {
                buttonPlayer1.setText("Robot");
            } else {
                buttonPlayer1.setText("Human");
            }
        });

        buttonPlayer2.addActionListener(e -> {
            if (buttonPlayer2.getText().equals("Human")) {
                buttonPlayer2.setText("Robot");
            } else {
                buttonPlayer2.setText("Human");
            }
        });

        buttonReset.addActionListener(e -> {

            if (buttonReset.getText().equals("Start")) {



                if (buttonPlayer1.getText().equals("Human") && buttonPlayer2.getText().equals("Human")) {
                    gameType = GameType.HUMAN_VS_HUMAN;
                    humanVsHuman();
                } else if (buttonPlayer1.getText().equals("Human") && buttonPlayer2.getText().equals("Robot")) {
                    gameType = GameType.HUMAN_VS_ROBOT;
                    humanVsComputer();
                } else if (buttonPlayer1.getText().equals("Robot") && buttonPlayer2.getText().equals("Human")) {
                    // computerVsHuman
                    gameType = GameType.ROBOT_VS_HUMAN;
                    chooseRandomCell();
                    humanVsComputer();

                } else {
                    //computerVsComputer
                    gameType = GameType.ROBOT_VS_ROBOT;
                    computerVsComputer();

                }
                initializeGame();
                buttonReset.setText("Reset");
                buttonPlayer1.setEnabled(false);
                buttonPlayer2.setEnabled(false);
            }

            else if (buttonReset.getText().equals("Reset")) {
                resetGame();
                buttonPlayer1.setEnabled(true);
                buttonPlayer2.setEnabled(true);
            }
        });
    }

    private void resetGame() {
        Cell.clickCounter = 1;
        for (Cell c: cells) {
            c.setText(" ");
            c.setEnabled(false);
        }

        board.totalMoves = 0;
        board.mostRecentMove = ' ';
        labelStatus.setText("Game is not started");
        buttonReset.setText("Start");
        isGameOver = false;
    }

    private void humanVsHuman() {
        for (Cell c: cells) {
            c.addActionListener(e -> {
                if (c.getText().equals(" ")) {

                    if (Cell.clickCounter % 2 == 1) {
                        c.setText("X");
                    } else {
                        c.setText("O");
                    }
                    updateInfoOnClick();
                    checkForWinner();

                }
            });
        }
    }

    private void humanVsComputer() {
        for (Cell c: cells) {
            c.addActionListener(e -> {
                if (c.getText().equals(" ")) {


                    if (Cell.clickCounter % 2 == 1) {
                        c.setText("X");
                    } else if (Cell.clickCounter % 2 == 0) {
                        c.setText("O");
                    }
                    updateInfoOnClick();
                    checkForWinner();

                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            chooseRandomCell();
                        }
                    }, 750);
                }
            });
        }

    }

    private void computerVsComputer() {
        java.util.Timer timer = new java.util.Timer();

        long timeInMillis = new Date(System.currentTimeMillis()).getTime();

        for (int i = 0; i < 9; i++) {
            try {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        chooseRandomCell();
                    }

                }, new Date(timeInMillis));
                timeInMillis += 1000;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void updateInfoOnClick() {
        Cell.clickCounter++;
        board.totalMoves++;
        String currentChar;
        String humanOrRobot;

        if (Cell.clickCounter % 2 == 1) {
            currentChar = "X";
        } else {
            currentChar = "O";
        }

        switch (gameType) {
            case HUMAN_VS_HUMAN -> labelStatus.setText(String.format(inProgress, "Human", currentChar));
            case HUMAN_VS_ROBOT -> {
                humanOrRobot = Cell.clickCounter % 2 == 0 ? "Robot" : "Human";
                labelStatus.setText(String.format(inProgress, humanOrRobot, currentChar));
            }
            case ROBOT_VS_HUMAN -> {
                humanOrRobot = Cell.clickCounter % 2 == 1 ? "Robot" : "Human";
                labelStatus.setText(String.format(inProgress, humanOrRobot, currentChar));
            }
            case ROBOT_VS_ROBOT -> labelStatus.setText(String.format(inProgress, "Robot", currentChar));
        }
    }


    private void checkForWinner() {
        String winnerString = "The %s Player (%s) wins";
        for (java.util.List<Cell> row: winningCombinations) {
            if (checkRow(row).equals("No Winner")) {
                continue;
            }

            String humanOrRobot;

            String currentChar = Cell.clickCounter % 2 == 0 ? "X" : "O";

            String winnerString1 = switch (gameType) {
                case HUMAN_VS_HUMAN -> String.format(winnerString, "Human", currentChar);
                case HUMAN_VS_ROBOT -> {
                    humanOrRobot = Cell.clickCounter % 2 == 0 ? "Robot" : "Human";
                    yield String.format(winnerString, humanOrRobot, currentChar);
                }

                case ROBOT_VS_HUMAN -> {
                    humanOrRobot = Cell.clickCounter % 2 == 1 ? "Robot" : "Human";
                    yield String.format(winnerString, humanOrRobot, currentChar);
                }

                case ROBOT_VS_ROBOT -> String.format(winnerString, "Robot", currentChar);
            };



            if (checkRow(row).equals("X") || checkRow(row).equals("O")) {

                labelStatus.setText(winnerString1);
                freezeCells();
                isGameOver = true;
                buttonPlayer1.setEnabled(true);
                buttonPlayer2.setEnabled(true);
                return;
            }
        }

        if (board.totalMoves == 9) {
            labelStatus.setText("Draw");
            freezeCells();
            isGameOver = true;
            buttonPlayer1.setEnabled(true);
            buttonPlayer2.setEnabled(true);
        }

    }

    private String checkRow(java.util.List<Cell> row) {
        Cell firstCell = row.get(0);
        Cell secondCell = row.get(1);
        Cell thirdCell = row.get(2);

        if (firstCell.getText().equals(" ") || secondCell.getText().equals(" ") || thirdCell.getText().equals(" ")) {
            return "No Winner";
        }

        if (firstCell.getText().equals(secondCell.getText())) {
            if (secondCell.getText().equals(thirdCell.getText())) {
                return firstCell.getText();
            }
        }
        return "No Winner";
    }

    private void freezeCells() {
        for (Cell c: cells) {
            c.setEnabled(false);
        }
    }



    private void initializeGame() {
        Cell.clickCounter++;
        updateInfoOnClick();
        Cell.clickCounter -= 2;
        board.totalMoves--;
        buttonPlayer1.setEnabled(false);
        buttonPlayer2.setEnabled(false);
        for (Cell c: cells) {
            c.setEnabled(true);
        }

    }

    private void chooseRandomCell() {
        if (isGameOver) {
            return;
        }
        Random random = new Random();
        int cellChoiceIndex = random.nextInt(9);
        Cell cellChoice = cells.get(cellChoiceIndex);
        while (!cellChoice.getText().equals(" ")) {
            cellChoiceIndex = random.nextInt(9);
            cellChoice = cells.get(cellChoiceIndex);
        }

        if (Cell.clickCounter % 2 == 1) {
            cellChoice.setText("X");
        } else {
            cellChoice.setText("O");
        }

        updateInfoOnClick();

        checkForWinner();
    }


    static class Cell extends JButton {
        private static int clickCounter = 1;


        public Cell(String text) {
            this.setText(text);
            this.setName("Button" + text);
            this.setPreferredSize(new Dimension(100, 100));
            //this.setFocusable(false);
            this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            this.setEnabled(false);
            this.setVisible(true);

        }
    }


    static class Board extends JPanel {
        int totalMoves = 0;
        char mostRecentMove = ' ';
        public Board() {
            this.setLayout(new GridLayout(3, 3));
            this.setPreferredSize(new Dimension(300, 300));
            this.setVisible(true);
        }
    }



}
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
        String ready = scanner.nextLine();

        if (ready.equals("ДА")) {
            System.out.println("Начинаем играть");

            Random random = new Random();
            int size = random.nextInt(2) + 7;
            int personX = 0;
            int personY = random.nextInt(size);

            int castleX = size - 1;
            int castleY = random.nextInt(size);

            int monsterX = random.nextInt(size);
            int monsterY = random.nextInt(size);

            while ((monsterX == personX && monsterY == personY) ||
                    (monsterX == castleX && monsterY == castleY)) {
                monsterX = random.nextInt(size);
                monsterY = random.nextInt(size);
            }

            boolean gameOver = false;

            while (!gameOver) {
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (x == personX && y == personY) {
                            System.out.print("P ");
                        } else if (x == castleX && y == castleY) {
                            System.out.print("C ");
                        } else if (monsterX != -1 && x == monsterX && y == monsterY) {
                            System.out.print("M ");
                        } else {
                            System.out.print(". ");
                        }
                    }
                    System.out.println();
                }

                System.out.println("Твоя позиция: (" + personX + ", " + personY + ")");
                if (monsterX != -1) {
                    System.out.println("Монстр: (" + monsterX + ", " + monsterY + ")");
                } else {
                    System.out.println("Монстра больше нет!");
                }
                System.out.println("Замок: (" + castleX + ", " + castleY + ")");

                System.out.print("Введи координаты хода (x y, через пробел): ");

                if (!scanner.hasNextInt()) {
                    scanner.next();
                    System.out.println("Вводи только числа! Попробуй ещё раз.");
                    continue;
                }
                int moveX = scanner.nextInt();
                int moveY = scanner.nextInt();

                if (moveX < 0 || moveX >= size || moveY < 0 || moveY >= size) {
                    System.out.println("Координаты вне карты! Попробуй ещё раз.");
                    continue;
                }

                int dx = Math.abs(moveX - personX);
                int dy = Math.abs(moveY - personY);
                if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
                } else {
                    System.out.println("Можно ходить только на 1 клетку вверх/вниз/влево/вправо (без диагоналей)!");
                    continue;
                }

                // Выполняем ход игрока
                personX = moveX;
                personY = moveY;

                if (personX == castleX && personY == castleY && monsterX == -1) {
                    System.out.println("Ты нашёл замок! Победа!");
                    gameOver = true;
                    break;
                }

                if (monsterX != -1 && personX == monsterX && personY == monsterY) {
                    if (solveMathChallenge(scanner, random)) {
                        System.out.println("Верно! Ты победил монстра!");
                        monsterX = -1; // убираем монстра
                        continue;
                    } else {
                        System.out.println("Неверно! Монстр оказался сильнее. Игра окончена.");
                        gameOver = true;
                        break;
                    }
                }

                if (monsterX != -1) {
                    if (monsterX < personX) {
                        monsterX++;
                    } else if (monsterX > personX) {
                        monsterX--;
                    } else if (monsterY < personY) {
                        monsterY++;
                    } else if (monsterY > personY) {
                        monsterY--;
                    }
                }

                if (monsterX != -1 && personX == monsterX && personY == monsterY) {
                    if (solveMathChallenge(scanner, random)) {
                        System.out.println("Верно! Ты отбился от монстра!");
                        monsterX = -1;
                        continue;
                    } else {
                        System.out.println("Неверно! Игра окончена.");
                        gameOver = true;
                        break;
                    }
                }
            }
        } else if (ready.equals("НЕТ")) {
            System.out.println("Приходи когда будешь");
        } else {
            System.out.println("Некорректный ввод");
            System.out.println("Перезапусти программу и введи либо 'ДА', либо 'НЕТ'");
        }

        scanner.close();
    }
    private static boolean solveMathChallenge(Scanner scanner, Random random) {
        int a = random.nextInt(90) + 10;
        int b = random.nextInt(90) + 10;
        int correctAnswer = a + b;

        System.out.println("Начинается сражение с монстром! Реши пример: " + a + " + " + b + " = ?");
        System.out.print("Твой ответ: ");

        if (!scanner.hasNextInt()) {
            scanner.next();
            return false;
        }

        int userAnswer = scanner.nextInt();

        return userAnswer == correctAnswer;
    }
}
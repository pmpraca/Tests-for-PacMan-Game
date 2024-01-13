package pt.isec.pa.tinypac.model.data.gameElements;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.data.Direction;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Ghost;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacManTest {

    @Test
    @DisplayName("Test if ghost eats pacman")
    void test1(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac ghost = new TinyPac(environment);
        Blinky pacman = new Blinky(environment);

        environment.setMazeElement(pacman, 2, 2);
        environment.setMazeElement(ghost, 2, 3);

        // Act
        pacman.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited 2nd - actual 3rd -waited
        assertEquals(ghost.getSymbol(),presentSymbol, "The awaited symbol is 'B'");
    }

    @Test
    @DisplayName("Test if pacman eats powered pellet")
    void test2(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);


        TinyPac tinyPac = new TinyPac(environment);
        PoweredPellet poweredPellet = new PoweredPellet(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(poweredPellet, 2, 3);

        // Act
        tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(tinyPac.getSymbol(),presentSymbol, "É waited que o símbolo seja 'M' ");
    }

    @Test
    @DisplayName("Test if pacman eats a dot")
    void test3(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);


        TinyPac tinyPac = new TinyPac(environment);
        Dot dot = new Dot(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(dot, 2, 3);

        // Act
        tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(tinyPac.getSymbol(),presentSymbol, "É waited que o símbolo seja 'M' ");
    }

    @Test
    @DisplayName("Test if pacman eats the fruit")
    void test4(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac tinyPac = new TinyPac(environment);
        Fruit fruit = new Fruit(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(fruit, 2, 3);
        environment.spawnFruit();

        // Act
        tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,4).getSymbol();

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(tinyPac.getSymbol(),presentSymbol, "The awaited symbol is M");
    }

    @Test
    @DisplayName("Test if after 20 pellets eaten the fruit appears")
    void test5(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac tinyPac = new TinyPac(environment);
        Dot dot = new Dot(environment);
        Fruit fruit = new Fruit(environment);

        environment.setMazeElement(tinyPac, 2, 0);
        for(int i=0; i<20; i++)
            environment.setMazeElement(dot, 2,1+i);

        environment.setMazeElement(fruit, 1, 1);

        // Act
        for(int i=0; i<20; i++)
            tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(1,1).getSymbol();
        System.out.println("score" + environment.getPUNCTUATION());
        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals('F',presentSymbol, "The awaited symbol is 'F'");
    }

    @Test
    @DisplayName("Test if pacman doesn´t trespass walls")
    void test6(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac tinyPac = new TinyPac(environment);
        Wall wall = new Wall(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(wall, 2, 3);

        // Act
        tinyPac.move();
        tinyPac.move();
        tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(wall.getSymbol(),presentSymbol, "The awaited symbol is 'x'");
    }

    @Test
    @DisplayName("Test if ghost doesn´t trespass walls")
    void test7(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        Blinky b = new Blinky(environment);
        Wall wall = new Wall(environment);

        environment.setMazeElement(b, 2, 2);
        environment.setMazeElement(wall, 2, 3);

        // Act
        b.move();


        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(wall.getSymbol(),presentSymbol, "The awaited symbol is 'B'");
    }

    @Test
    @DisplayName("Test if pacman eats ghost")
    void test8(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);


        Ghost blinky = new Blinky(environment);
        TinyPac tinyPac = new TinyPac(environment);

        tinyPac.setPoweredPelletEated(true);
        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(blinky, 2, 3);

        // Act
        tinyPac.move();

        // Assert
        char presentSymbol = environment.getMazeElement(2,3).getSymbol();

        // 1st - waited result 2nd - actual result 3rd - String Informs what was the expected result
        assertEquals(tinyPac.getSymbol(),presentSymbol, "É waited que o símbolo seja 'M' ");

    }

    @Test
    @DisplayName("PacMan losing a life when eated")
    void test9(){

        //  Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        Blinky b = new Blinky(environment);
        TinyPac pacman = new TinyPac(environment);

        environment.setMazeElement(pacman, 2, 1);
        environment.setMazeElement(b, 2, 2);

        // Act
        pacman.move();


        // Assert
        int actual = environment.getLIVES();
        System.out.println("Lives: " + environment.getLIVES());

        // 1st - waited 2nd - actual 3rd -    waited
        assertEquals(2,actual, "The awaited symbol is '2'");
    }
}

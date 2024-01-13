package pt.isec.pa.tinypac.model.datas.score;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.data.Direction;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.gameElements.Dot;
import pt.isec.pa.tinypac.model.data.gameElements.Fruit;
import pt.isec.pa.tinypac.model.data.gameElements.PoweredPellet;
import pt.isec.pa.tinypac.model.data.gameElements.TinyPac;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.states.FrightenedState;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    @DisplayName("Test when dot is eaten the score changes correctly")
    void test1(){

        // Methodology 3 A's
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
        int presentScore = environment.getPUNCTUATION();

        // 1st -  waited 2nd - actual 3rd -   waited
        assertEquals(1,presentScore, "The awaited score is 1 ");
    }

    @Test
    @DisplayName("Test when PoweredPellet is eaten the score changes correctly")
    void test2(){

        // Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac tinyPac = new TinyPac(environment);
        PoweredPellet pp = new PoweredPellet(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(pp, 2, 3);

        // Act
        tinyPac.move();
        
        // Assert
        int presentScore = environment.getPUNCTUATION();

        // 1st - waited 2nd - actual 3rd - waited
        assertEquals(10,presentScore, "The awaited score is 10 ");
    }

    @Test
    @DisplayName("Test when a Fruit is eaten the score changes correctly")
    void test3(){

        // Methodology 3 A's
        //
        // Arrange (setup test)
        Environment environment = new Environment(31, 29);
        environment.changeDirection(Direction.RIGHT);

        TinyPac tinyPac = new TinyPac(environment);
        Fruit fruit= new Fruit(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(fruit, 2, 3);
        environment.spawnFruit();

        // Act
        tinyPac.move();

        // Assert
        int presentScore = environment.getPUNCTUATION();

        // 1st - waited 2nd - actual 3rd -   waited
        assertEquals(26,presentScore, "The awaited score is 26 ");
    }

    @Test
    @DisplayName("Test if the score after eating multiple ghosts increments correctly")

    void test4() throws IOException {

        // Arrange
        Environment environment = new Environment(31, 29);
        environment.setAllFree(true);
        environment.setLockGhosts(true);
        environment.changeDirection(Direction.RIGHT);
        EnvironmentManager environmentManager = new EnvironmentManager(environment);

        TinyPacContext context = new TinyPacContext();
        context.changeState(new FrightenedState(context,environmentManager));

        TinyPac tinyPac = new TinyPac(environment);
        Blinky blinky = new Blinky(environment);

        tinyPac.setPoweredPelletEated(true);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(blinky, 2, 3);

        // Act
        tinyPac.move();

        System.out.println("score: " + environment.getPUNCTUATION());

        // Assert
        int presentScore = environment.getPUNCTUATION();

        assertEquals(50,presentScore,"Awaited Score: 50");

    }
    @Test
    @DisplayName("Test the incrementation of score after eating multiple ghosts")

    void test5() throws IOException {
        long time = 112233445L;

        // Arrange
        Environment environment = new Environment(31, 29);
        environment.setAllFree(true);
        environment.setLockGhosts(true);
        environment.changeDirection(Direction.RIGHT);
        EnvironmentManager environmentManager = new EnvironmentManager(environment);

        TinyPacContext context = new TinyPacContext();
        context.changeState(new FrightenedState(context,environmentManager));

        TinyPac tinyPac = new TinyPac(environment);
        Blinky blinky = new Blinky(environment);
        Pinky pinky = new Pinky(environment);
        Inky inky = new Inky(environment);
        Clyde clyde = new Clyde(environment);

        tinyPac.setPoweredPelletEated(true);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(blinky, 2, 3);
        environment.setMazeElement(pinky, 2, 4);
        environment.setMazeElement(inky, 2, 5);
        environment.setMazeElement(clyde, 2, 6);

        // Act
        for(int i = 0; i < 8; i++){
            context.evolve(time);
            System.out.println("score: " + environment.getPUNCTUATION());
            time += time;
        }

        // Assert

        int presentScore = environment.getPUNCTUATION();
        assertEquals(300,presentScore,"Awaited Score: 300");

    }



}

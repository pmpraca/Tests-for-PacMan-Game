package pt.isec.pa.tinypac.model.fsm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.data.Direction;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.gameElements.PoweredPellet;
import pt.isec.pa.tinypac.model.data.gameElements.TinyPac;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.gameElements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.states.ChaseState;
import pt.isec.pa.tinypac.model.fsm.states.FrightenedState;
import pt.isec.pa.tinypac.model.fsm.states.ReadyState;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FSMContextTest {

    @Test
    @DisplayName("Go from ReadyState to GhostsCagedState")

    void test1() throws IOException {
    // Arrange

    TinyPacContext context = new TinyPacContext(); // já está no ReadyState

    // Act
    context.pressedKey(Direction.UP);
    TinyPacState actualState = context.getState();

    // Assert
    assertEquals(TinyPacState.GHOSTS_CAGED,actualState,"Awaited state is GHOSTS_CAGED STATE");

    }

    @Test
    @DisplayName("Go from ChaseState to PausedState")

    void test2() throws IOException {
        // Arrange
        EnvironmentManager environmentManager = new EnvironmentManager();
        TinyPacContext context = new TinyPacContext(); // já está no ReadyState
        context.changeState(new ChaseState(context,environmentManager));

        // Act
        context.pauseGame();
        TinyPacState actualState = context.getState();

        // Assert
        assertEquals(TinyPacState.PAUSED,actualState,"Awaited state is PAUSED_STATE");

    }

    @Test
    @DisplayName("Trying to pause the game during ReadyState")

    void test3() throws IOException {
        // Arrange
        TinyPacContext context = new TinyPacContext();

        // Act
        context.pauseGame();
        TinyPacState actualState = context.getState();

        // Assert
        assertEquals(TinyPacState.READY,actualState,"Awaited state is READY_STATE");

    }

    @Test
    @DisplayName("Go to FrightenedState after pacman eats powered pellet")

    void test4() throws IOException {

        long time = 112233445L;

        // Arrange
        Environment environment = new Environment(31, 29);
        environment.setAllFree(true);
        environment.changeDirection(Direction.RIGHT);

        EnvironmentManager environmentManager = new EnvironmentManager(environment);

        TinyPacContext context = new TinyPacContext();
        context.changeState(new ChaseState(context,environmentManager));

        TinyPac tinyPac = new TinyPac(environment);
        PoweredPellet poweredPellet = new PoweredPellet(environment);

        environment.setMazeElement(tinyPac, 2, 2);
        environment.setMazeElement(poweredPellet, 2, 3);

        // Act
        //tem de fazer alguns evolves pois o pacman pois demora até o pacman evoluir devido
        //à lógica que o faz mais lento
        for(int i = 0; i < 5; i++){
            context.evolve(time);
            System.out.println("Actual State: " + context.getState());
            time += time;
        }

        // Assert
        TinyPacState actualState = context.getState();
        assertEquals(TinyPacState.FRIGHTENED,actualState,"Awaited state is FRIGHTENED_STATE");

    }

    @Test
    @DisplayName("From FrigthenedState state to ChaseState after eating all the ghosts")

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
        //tem de fazer alguns evolves pois o pacman pois demora até o pacman evoluir devido
        //à lógica que o faz mais lento
        for(int i = 0; i < 8; i++){
            context.evolve(time);
            System.out.println("ESTADO actual: " + context.getState());
            time += time;
        }

        // Assert
        TinyPacState actualState = context.getState();
        assertEquals(TinyPacState.CHASE,actualState,"O estado waited é CHASE_STATE");

    }

    @Test
    @DisplayName("From ChaseState to GameEndedState after finishing last level 20 (winning the game)")

    void test6() throws IOException {
        long time = 112233445L;

        // Arrange
        Environment environment = new Environment(31, 29);
        EnvironmentManager environmentManager = new EnvironmentManager(environment);
        environmentManager.setLevelDesired(20);
        environment.poweredPelletCounter();
        environment.poweredPelletCounter();
        environment.poweredPelletCounter();
        environment.poweredPelletCounter();
        environment.dotCounter();
        TinyPacContext context = new TinyPacContext();
        context.changeState(new ChaseState(context,environmentManager));

        // Act
        context.evolve(time);

        // Assert
        TinyPacState actualState = context.getState();
        assertEquals(TinyPacState.GAME_ENDED,actualState,"Awaited state is GAME_ENDED_STATE");

    }

    @Test
    @DisplayName("When FrigthenedState state duration ends game state changes to ChaseState")

    void test7() throws IOException {
        long time = 112233445L;

        // Arrange
        Environment environment = new Environment(31, 29);
        environment.setAllFree(true);
        environment.setLockGhosts(true);
        environment.changeDirection(Direction.RIGHT);
        EnvironmentManager environmentManager = new EnvironmentManager(environment);

        TinyPacContext context = new TinyPacContext();
        context.changeState(new FrightenedState(context,environmentManager));

        // Act
        //tem de fazer alguns evolves pois o pacman pois demora até o pacman evoluir devido
        //à lógica que o faz mais lento
        for(int i = 0; i < 10; i++){
            context.evolve(time);
            System.out.println("actual state: " + context.getState());
            time += time;
        }

        // Assert
        TinyPacState actualState = context.getState();
        assertEquals(TinyPacState.CHASE,actualState," Awaited state is CHASE_STATE");

    }

    @Test
    @DisplayName("Verifying chaging of levels is working")

    void test8() throws IOException {

        // Arrange
        Environment environment = new Environment(31, 29);
        EnvironmentManager environmentManager = new EnvironmentManager(environment);

        TinyPacContext context = new TinyPacContext();
        context.changeState(new ReadyState(context,environmentManager));

        environment.setNoMoreDots();
        environment.setNoMorePoweredPellets();

        // Assert
        TinyPacState actualState = context.getState();
        assertEquals(TinyPacState.READY,actualState,"Awaited state is READY_STATE");

    }



}

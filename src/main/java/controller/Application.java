package controller;

import model.ModifiableArena;
import model.ModifiableSnake;
import model.strategy.bono.BonoStrategy;

public class Application
{
    /**
     *   <repositories>
     <repository>
     <snapshots>
     <enabled>false</enabled>
     </snapshots>
     <id>central</id>
     <name>Central Repository</name>
     <url>http://repo.maven.apache.org/maven2</url>
     </repository>
     </repositories>

     <pluginRepositories>
     <pluginRepository>
     <releases>
     <updatePolicy>never</updatePolicy>
     </releases>
     <snapshots>
     <enabled>false</enabled>
     </snapshots>
     <id>central</id>
     <name>Central Repository</name>
     <url>http://repo.maven.apache.org/maven2</url>
     </pluginRepository>
     </pluginRepositories>
     * @param args
     */
    public static void main(String[] args)
    {
        System.setSecurityManager(new SecurityManager());

        ModifiableArena arena = new ModifiableArena();

        ModifiableSnake snake1 = new ModifiableSnake(arena, new BonoStrategy(), "Bono One");
        ModifiableSnake snake2 = new ModifiableSnake(arena, new BonoStrategy(), "Bono Two");

        arena.addSnake(snake1);
        arena.addSnake(snake2);

        new SnakeController(arena).start();
    }
}

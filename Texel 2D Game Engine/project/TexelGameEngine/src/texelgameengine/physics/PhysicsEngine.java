package texelgameengine.physics;

import java.util.Vector;

import texelgameengine.game.GameObject;
import texelgameengine.game.GameWorld;
import texelgameengine.world.GameMap;

public class PhysicsEngine 
{
    // @Deprecated
    // // private static double airDensity;
    // @Deprecated
    // // private static boolean airResistance;
    private static float gravity = 100;

    private PhysicsEngine() {}

    public static void calculateEntityMotion(GameEntity e, GameMap map, float tickSpeed)
    {
        synchronized(GameWorld.getProcessLock()){
            if(e == null) return;

            // System.out.println(System.nanoTime());

            //Collision type check
            if(map != null && e.hitboxEnabled()){
                Vector<GameObject> collisionBlocks = map.getBlocksInRadius((int)e.getX(), (int)e.getY(), (int)e.getWidth());
                for(GameObject go : collisionBlocks){
                    int collisionType = e.collision(go, false);
                    if(collisionType == GameEntity.NO_COLLISION){
                        continue;
                    }
                    else if(collisionType == GameEntity.SIDE_COLLISION){
                        e.setTime(0);
                        e.setInitial(new InitialPoint(e.getX(), e.getY(), GameEntity.STANDING_VECTOR));
                        continue;
                    }
                    else if(collisionType == GameEntity.FLOOR_COLLISION){
                        e.setTime(0);
                        e.setInitial(new InitialPoint(e.getX(), e.getY(), GameEntity.STANDING_VECTOR));
                        return;
                    }
                }
            }
            // System.out.println(System.nanoTime());

            // if(e.isGrounded()) return e;

            e.setX(e.getInitial().getX() + e.getInitial().getHorizontalVelocity().getVelocity()*e.getTime());
            e.setY(e.getInitial().getY() + e.getInitial().getVerticalVelocity().getVelocity()*e.getTime() + (-gravity/2)*(e.getTime()*e.getTime()));

            e.setTime(e.getTime()+tickSpeed/1000);  //convert ticks per second (tickSpeed) to instantaneous tick
        }
    }

    /**
     * calculate motion for non-projectile objects. returns a float precision coordinate based on the elapsed time
     */
    public static java.awt.geom.Point2D.Float calculateEntityMotion(InitialPoint initial, float elapsedTime) {
        float x = initial.getX() + initial.getHorizontalVelocity().getVelocity()*elapsedTime;
        float y = initial.getY() + initial.getVerticalVelocity().getVelocity()*elapsedTime + (-gravity/2)*(elapsedTime*elapsedTime);
        return new java.awt.geom.Point2D.Float(x,y);
    }

    /**
     * explode all 
     */
    // public static void propagateExplosion(ExplosiveProjectile explosive, GameMap map, Vector<Projectile> projectiles){
    //     GameChunk[] mapProjectiles = map.getChunksWithinRange(explosive.getX(), explosive.getBlastRadius());

    //     for(int i = 0; i < mapProjectiles.length; i++)
    //         for(int j = 0; j < )

    //     for(int i = 0; i < projectiles.size(); i++)
    //     {
    //         Projectile p = projectiles.get(i);
    //         if(p instanceof ExplosiveProjectile){
    //             projectiles.remove(i);
    //             continue;
    //         }

    //         double distance = Math.sqrt(Math.pow(explosive.getX()-e.getX(), 2)+Math.pow(explosive.getY()-e.getY(), 2));
    //         double velocity = (e.getMass()/(distance/explosive.getBlastRadius())); //i am cheating and using acceleration as velocity since air resistance has not been implemented yet
            
    //         //calculate angle
    //         double cx = e.getX() - explosive.getX();
    //         double cy = e.getY() - explosive.getY();

    //         double theta = Math.atan2(cy, cx)*(180/Math.PI);
    //         theta = theta>=0 ? theta : 360+theta;

    //         //construct new initial point
    //         e.setInitial(new InitialPoint(e.getX(), e.getY(), new physics.Vector(velocity, theta)));
    //         e.setGrounded(false);

    //         // System.out.println(e.initial);

    //         projectiles.set(i, p);
    //     }
    //     return projectiles;
    // }

    public float getGravity() { return PhysicsEngine.gravity; }
    public void setGravity(float gravity) { PhysicsEngine.gravity = gravity; }
}

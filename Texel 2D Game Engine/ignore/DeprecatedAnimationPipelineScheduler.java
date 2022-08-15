// package graphics;

// import java.util.Vector;

// public class AnimationPipelineScheduler extends PaintableObject
// {
//     private static Vector<Animation> animations;

//     private AnimationPipelineScheduler(){ super(PaintableObject.TOP_LAYER); }

//     public static void registerAnimation(Animation a) {
//         if(!a.isRegisteredInPipeline()){
//             int i = 0;
//             while(a.getZLayer() > animations.get(i).getZLayer()) //insert 

//             animations.add(i, a);
//             a.setRegisteredInPipeline(true);
//         }
//     }
    
//     @Override
//     public void paintObject(GraphicsEvent e) {
//         long currentTime = System.currentTimeMillis();
//         for(int i = 0; i < animations.size(); i++){
//             Animation a = animations.get(i);
//             if(a.getMaxCycles() == Animation.DURATION_FOREVER){
//                 a.paintObject(e); 
//             }
//             else if((int)((a.getFramesDrawn()%a.getFrames().length)/a.getFrames().length) < a.getMaxCycles()){
//                 a.paintObject(e);
//                 if((a.getInitTime()-currentTime)%a.getDelay() < 250) //10 millis of error
//                     a.incFramesDrawn();
//             }
//             if(a.getFramesDrawn()%a.getFrames().length > a.getMaxCycles()) 
//                 animations.remove(i);
//         }
//     }

//     public static Vector<Animation> getAnimations() { return animations; }
// }

package gameui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class ScaledLayout implements java.awt.LayoutManager{

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {
        
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized(parent.getTreeLock()){
            return new Dimension(0,0);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized(parent.getTreeLock()){
            return new Dimension(0,0);
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized(parent.getTreeLock()){
            Component[] comp = parent.getComponents();
            int pWidth = parent.getWidth();
            int pHeight = parent.getHeight();

            for(int i = 0; i < comp.length; i++){
                if(comp[i] instanceof ScaledLayoutValues){
                    ScaledLayoutValues c = (ScaledLayoutValues)comp[i];

                    comp[i].setSize((int)(parent.getWidth()*c.getWidthDimensionCoefficient()), (int)(parent.getHeight()*c.getHeightDimensionCoefficient()));

                    int nx = (int)(pWidth*c.getWidthPositionCoefficient())-(int)(comp[i].getSize().getWidth()/2);
                    int ny = (int)(pHeight*c.getHeightPositionCoefficient())-(int)(comp[i].getSize().getHeight()/2);
                    
                    comp[i].setBounds(nx, ny, comp[i].getWidth(), comp[i].getHeight());
                }
            }
        }
    }
}

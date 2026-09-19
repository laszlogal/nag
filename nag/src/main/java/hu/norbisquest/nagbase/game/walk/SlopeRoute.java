 package hu.norbisquest.nagbase.game.walk;

import hu.norbisquest.nagbase.game.App;

import java.util.ArrayList;
import java.util.List;

 public class SlopeRoute implements RouteInterface {
     int step=0;


     class Slope {

         WalkPoint p;
         WalkPoint q;

         int index = 0;
         int dx;
         int dy;
         String label;

         Slope(WalkPoint p, WalkPoint q, String label) {
             this.p = p;
             this.q = q;
             this.label = label;
         }

         WalkPoint next() {
             dx = (int) Math.abs(p.getX() - q.getX());
             dy = (int) Math.abs(p.getY() - q.getY());
             int stepX = q.getX() < p.getX() ? -step : step;
             int stepY = q.getY() < p.getY() ? -step : step;
             int x;
             int y;
             if (dx > dy) {
                 y = (int) p.getY();
                 x = (int) (p.getX() + stepX);
                 if (2 * dy - dx > 0) {
                     y += stepY;
                 }
             } else {
                 x = (int) p.getX();
                 y = (int) (p.getY() + stepY);
                 if (2 * dx - dy > 0) {
                     x += stepX;
                 }
             }
             p.setX(x);
             p.setY(y);
             p.setLabel(label);
             return p;
         }

         @Override
         public String toString() {
             return "    " + label;
         }

         boolean isEnd() {
             boolean same = p.same(q, 10);
             App.debug("Same " + startPoint + " " + q + ": " + same);
             return same;
         }


     }

     private List<Slope> slopes = new ArrayList<>();
     private WalkPoint startPoint;
     private WalkPoint endPoint = null;
     private boolean ready = false;

     public SlopeRoute(int step) {
         this.step = step;
     }

     @Override
     public boolean isValid() {
         return isReady() && !isEmpty();
     }

     @Override
     public WalkPoint next() {
         if (slopes.size() < 1) {
             return null;
         }
         WalkPoint p = null;
         Slope slope = slopes.get(0);
         if (slope != null) {
             p = slope.next();
             if (slope.isEnd()) {
                 slopes.remove(0);
             }
         }
         return p;
     }

     @Override
     public WalkPoint getPointAt(int index) {
         return endPoint;
     }

     @Override
     public void add(WalkPoint p) {

     }

     @Override
     public void add(WalkPoint p, WalkPoint q, String label) {
         if (isEmpty()) {
             startPoint = p;
         }
         slopes.add(new Slope(p, q, label));
     }

     @Override
     public void clear() {
         slopes.clear();

     }

     @Override
     public boolean isEmpty() {
         return slopes.isEmpty();
     }

     @Override
     public boolean isSingle() {
         return slopes.size() == 1 && "".equals(slopes.get(0).label);
     }

     @Override
     public WalkPoint getEnd() {
         return slopes.get(slopes.size() - 1).q;
     }

     @Override
     public String toString() {
         StringBuilder sb = new StringBuilder();
         for (Slope slope : slopes) {
             sb.append("    ").append(slope.toString());
         }
         return sb.toString();
     }

     public boolean isReady() {
         return ready;
     }

     void setReady(boolean ready) {
         if (ready && slopes.size() > 0) {
             endPoint = getEnd();
         }
         this.ready = ready;
     }
 }

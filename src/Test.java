import java.io.Serializable;

public class Test {
    public class Box implements Serializable{
        private int w;
        private int h;

        public Box(int w,int h){
            this.w = w;
            this.h = h;
        }

        public int getW(){
            return w;
        }

        public void setW(int w){
            this.w = w;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }


    }
}


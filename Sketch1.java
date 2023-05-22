import processing.core.PApplet;

public class Sketch1 extends PApplet {

    public void settings() {
        size(500, 500, P3D);
    }

    public void setup() {
        background(255);//背景を黒に
        noFill();//描画する際に塗りつぶさない
        stroke(0);//枠線を黒に
    }


    public void draw() {
//        rotateY(frameCount * 0.01f);
        background(255);//白で塗りつぶす
        //ortho(-width/2,width/2,-height/2,height/2);//平行投影法（無限遠点から見ている）
        //perspective(radians(60),1,100,-100);//透視投影法（遠近法）
        arrow(0, 0, 0, 150, 0, 0, 150, 0, 0);//X軸（赤）
        arrow(0, 0, 0, 0, 150, 0, 0, 150, 0);//Y軸（緑）
        arrow(0, 0, 0, 0, 0, 150, 0, 0, 150);//Z軸（青）
        //ここから下にプログラムを書く
        camera(0,0,250,0,0,0,0,1,0);//カメラの位置(1)
        //camera(250,200,100,0,0,0,0,0,-1); //カメラの位置(2)
        //camera(-250,200,100,0,0,0,0,0,-1);//カメラの位置(3)
        //camera(-250,-200,100,0,0,0,0,0,-1);//カメラの位置(4);
        //camera(250,-200,100,0,0,0,0,0,-1);//カメラの位置(5);
//        rotateZ(PI/6);
//        translate(50,50);   //手順(1)
//        rotateZ(PI/6);      //手順(2)
        //fill(255);白で塗りつぶす
        box(50);

    }


    //以下は3次元の座標系を描画するためのものなのでスルーでOK
    void arrow(int x1, int y1, int z1, int x2, int y2, int z2, int Color1, int Color2, int Color3) {
        int arrowLength = 10;
        float arrowAngle = 0.5f;
        float phi = -atan2(y2 - y1, x2 - x1);
        float theta = PI / 2 - atan2(z2 - z1, x2 - x1);
        stroke(Color1, Color2, Color3);
        strokeWeight(4);
        line(x1, y1, z1, x2, y2, z2);
        strokeWeight(1);
        pushMatrix();
        translate(x2, y2, z2);
        rotateY(theta);
        rotateX(phi);
        cone(arrowLength, arrowLength * sin(arrowAngle), Color1, Color2, Color3);
        popMatrix();
    }

    void cone(int L, float radius, int Color1, int Color2, int Color3) {
        float x, y;
        noStroke();
        fill(Color1, Color2, Color3);
        beginShape(TRIANGLE_FAN);  // 底面の円の作成
        vertex(0, 0, -L);
        for (float i = 0; i < 2 * PI; ) {
            x = radius * cos(i);
            y = radius * sin(i);
            vertex(x, y, -L);
            i += 0.01f;
        }
        endShape(CLOSE);
        beginShape(TRIANGLE_FAN);  // 側面の作成
        vertex(0, 0, 0);
        for (float i = 0; i < 2 * PI; ) {
            x = radius * cos(i);
            y = radius * sin(i);
            vertex(x, y, -L);
            i += 0.01f;
        }
        endShape(CLOSE);
        noFill();
        stroke(0);
    }

    public static void main(String[] args) {
        PApplet.main(new Object() {
        }.getClass().getEnclosingClass().getName());
    }
}

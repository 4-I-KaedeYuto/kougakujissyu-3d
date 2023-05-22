import processing.core.PApplet;
import processing.event.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sketch2 extends PApplet {
    float camera_r = 400;
    float theta_x = 0;
    float theta_y = 0;

    AstronomicalObject sun;
    Planet mercury;
    Planet venus;
    Planet earth;
    Planet mars;
    Planet jupiter;
    Planet saturn;
    Planet uranus;
    Planet neptune;
    Satellite moon;

    public void settings() {
        size(500, 500, P3D);
    }

    public void setup() {

        background(0);//背景を黒に
        noFill();//描画する際に塗りつぶさない
        stroke(255);//枠線を白に

        arrow(0, 0, 0, 150, 0, 0, 150, 0, 0);//X軸（赤）
        arrow(0, 0, 0, 0, 150, 0, 0, 150, 0);//Y軸（緑）
        arrow(0, 0, 0, 0, 0, 150, 0, 0, 150);//Z軸（青）
        sun = new AstronomicalObject(100, Color.RED);
        mercury = new Planet(5f, 110f, 0.241f, Color.decode("#f4a460"));
        venus = new Planet(15f, 150f, 0.615f, Color.decode("#f0e68c"));
        earth = new Planet(16f, 200f, 1.0f, Color.decode("#1e90ff"));
        mars = new Planet(8f, 300f, 1.881f, Color.decode("#b22222"));
        jupiter = new Planet(192f, 1000f, 11.862f, Color.decode("#ffe4e1"));
        saturn = new Planet(160f, 1800f, 29.457f, Color.decode("#cd853f"));
        uranus = new Planet(64f, 3800f, 84.011f, Color.decode("#afeeee"));
        neptune = new Planet(63f, 6000f, 164.79f, Color.decode("#1e90ff"));
        moon = new Satellite(3f, 22f, 0.083f, Color.decode("#f8f8ff"));

        sun.children.add(mercury);
        sun.children.add(venus);
        sun.children.add(earth);
        sun.children.add(mars);
        sun.children.add(jupiter);
        sun.children.add(saturn);
        sun.children.add(uranus);
        sun.children.add(neptune);

        earth.children.add(moon);

    }


    public void draw() {
        background(0);//黒で塗りつぶす
        //ortho(-width/2,width/2,-height/2,height/2);//平行投影法（無限遠点から見ている）
        perspective(radians(60),1,100,-100000);//透視投影法（遠近法）
        arrow(0, 0, 0, 150, 0, 0, 150, 0, 0);
        arrow(0, 0, 0, 0, 150, 0, 0, 150, 0);
        arrow(0, 0, 0, 0, 0, 150, 0, 0, 150);
        //ここから下にプログラムを書く
        camera(camera_r * cos(theta_x) * cos(theta_y), camera_r * sin(theta_x) * cos(theta_y), camera_r * sin(theta_y),
                0, 0, 0,
                0, 0, -1); //カメラの位置
        sun.draw();
        //sun
//        noStroke();//球体の枠線を消す
//        fill(255, 0, 0);//表面赤色に
//        sphere(60);//半径60の球体
//
//        pushMatrix();
//        pushStyle();
//        fill(255);//表面白色に
//        stroke(255, 0, 0);//枠線赤色に
//        rotateZ(frameCount * 0.01f);
//        translate(100, 0, 0);
//        box(30);//大きさを100
//
//        popStyle();
//        popMatrix();
//
//        pushMatrix();
//        pushStyle();
//        fill(0, 255, 0);
//        noStroke();
//        rotateX(frameCount * 0.01f);
//        translate(0, 100, 0);
//        sphere(10);
//        popMatrix();
//        popStyle();

    }

    public void mouseDragged(){
        theta_x += (mouseX - pmouseX) * PI / 500;
        theta_x %= 2 * PI;
        theta_y += (mouseY - pmouseY) * PI / 500;
        if (theta_y > PI / 2) theta_y = PI / 2;
        if (theta_y < -PI / 2) theta_y = -PI / 2;
    }

    public void mouseWheel(MouseEvent e){
        camera_r += e.getCount() * 100;
        if (camera_r < 10) camera_r = 10;
        if (camera_r > 100000) camera_r = 100000;
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
            i += 0.01;
        }
        endShape(CLOSE);
        beginShape(TRIANGLE_FAN);  // 側面の作成
        vertex(0, 0, 0);
        for (float i = 0; i < 2 * PI; ) {
            x = radius * cos(i);
            y = radius * sin(i);
            vertex(x, y, -L);
            i += 0.01;
        }
        endShape(CLOSE);
        noFill();
        stroke(0);
    }

    class AstronomicalObject{
        public List<AstronomicalObject> children;
        public float radius;
        Color cl;

        public AstronomicalObject(float r, Color cl){
            this.radius = r;
            this.children = new ArrayList<>();
            this.cl = cl;
        }

        public void draw(){
            pushMatrix();
            pushStyle();

            noStroke();
            fill(cl.getRGB(), cl.getAlpha());
            sphere(radius);

            for (AstronomicalObject child : children) {
                child.draw();
            }

            popStyle();
            popMatrix();
        }
    }

    class Planet extends AstronomicalObject{
        public float distance;
        public float t;
        private float theta = 0;

        public Planet(float r, float d, float t, Color cl){
            super(r, cl);
            this.distance = d;
            this.t = t;
        }

        public void draw(){
            pushMatrix();
            pushStyle();

            noStroke();
            fill(cl.getRGB(), cl.getAlpha());
            theta += PI / 300 / t;
            rotateZ(theta);
            translate(distance, 0, 0);
            sphere(radius);

            for (AstronomicalObject child : children) {
                child.draw();
            }

            popStyle();
            popMatrix();
        }
    }

    class Satellite extends Planet{
        public Satellite(float r, float d, float t, Color cl) {
            super(r, d, t, cl);
        }
    }

    public static void main(String[] args) {
        PApplet.main(new Object() {
        }.getClass().getEnclosingClass().getName());
    }
}

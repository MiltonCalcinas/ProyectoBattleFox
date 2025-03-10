package battle.fox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class ConfigurarSuperficies {

    private Array<Rectangle> superficies;
    private ShapeRenderer shapeRenderer;


    public Array<Rectangle> ArraySuperficies(){
        return superficies;
    }

    public void printarSuperficies(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        shapeRenderer.setColor(0, 0, 0, 0);
        //printar superficies
        for(int i=0;i<superficies.size;i++){
            shapeRenderer.rect(superficies.get(i).x,superficies.get(i).y,superficies.get(i).width,superficies.get(i).height);
        }

    }

    public void forNivel(int nivelI) {
        if (nivelI == 1) {
            establecerRectableNivel1();
        } else if (nivelI == 2) {
            establecerRectableNivel2();
        } else if (nivelI == 3) {
            establecerRectableNivel3();
        } else if (nivelI == 4) {
            establecerRectableNivel4();
        } else if (nivelI == 5) {
            establecerRectableNivel5();
        } else if (nivelI == 6) {
            establecerRectableNivel6();
        }

    }

    public void establecerRectableNivel1() {

        int[][] datos = new int[][]{{0, 0, Gdx.graphics.getWidth(), 40}, // suelo
            {0, 0, 55, Gdx.graphics.getHeight()}, // pared izq
            {1320, 0, 100, Gdx.graphics.getHeight()},// pared der
            {55, 300, 365, 70}, // 1
            {360, 250, 215, 65}, // 2
            {565, 195, 100, 65},// 3
            {665, 135, 110, 65}, // 4
            {775, 90, 155, 55},// 5
            //{930,135,125,65},//6
            {1000, 200, 130, 115},//7
            {1100, 200, 200, 60}, // 8
            {1270, 200, 130, 115},//9
            {260, 40, 218, 65}//10
        }; // 5

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }
    public void establecerRectableNivel2() {

        int[][] datos = new int[][]{{0, 0,1115, 60}, // suelo
            {1250,0,180,60},
            {0, 0, 1, Gdx.graphics.getHeight()}, // pared izq
            {Gdx.graphics.getWidth()-5, 0, 1, Gdx.graphics.getHeight()},// pared der
            {180, 400, 280, 35}, // 1
            {455, 350, 40, 40}, // 2
            {495, 310, 40, 40},// 3
            {535, 310, 35, 30},// 4
            {570, 280, 35, 30},// 5
            {687, 320, 140, 33}, // 6
            {890, 180, 220, 1},// 7
            {1160, 140, 90, 1},//8
            {1250, 100, 200, 80}, // 9
            {1290, 180, 130, 90},//10
            {840,60,120,45}, // 11
            {610, 145, 80, 1}, //12
            {570, 145, 40, 40},
            {450, 185, 120, 40},
            {100, 250, 40, 20},
        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }


    public void establecerRectableNivel3() {

        int[][] datos = new int[][]{{0, 0,Gdx.graphics.getWidth(), 30}, // suelo
            {0, 0, 1, Gdx.graphics.getHeight()}, // pared izq
            {Gdx.graphics.getWidth()-5, 0, 1, Gdx.graphics.getHeight()},// pared der
            {0, 390, 180, 1}, // 1
            {180, 390, 20, 20}, // 2
            {200, 410, 20, 20},// 3
            {225, 450, 190, 1},// 4
            {400, 320, 175, 1},// 5
            {855, 300, 120, 1}, // 6
            {340, 200, 670, 30}, // 7
            {1020, 160, 20, 20},//8
            {1040, 140, 20, 20}, // 9
            {1060, 115, 30, 20}, // 10
            {1095, 115, 30, 1}, // 11
            {1120, 90, 60, 1}, // 12
            {1280, 120, 100, 1},//13
            {365,90, 520, 1}, //14
            {90,90,30,1},// 15
            {130,130,30,1},
            {180,150,30,1},
            {220,180,30,1},
            {250,180,30,1},
            {270,190,80,1}

        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }

    public void establecerRectableNivel4() {

        int[][] datos = new int[][]{{0, 0,Gdx.graphics.getWidth(), 40}, // suelo
            {0, 0, 1, Gdx.graphics.getHeight()}, // pared izq
            {Gdx.graphics.getWidth()-5, 0, 1, Gdx.graphics.getHeight()},// pared der
            {60, 330, 430, 1}, // 1
            {260, 215, 330, 1}, // 2
            {260, 115, 310, 1}, // 2
            {0, 160, 270, 1}, // 2
            {755, 160, 230, 1}, // 2
            {990,215,140,1},
            {990,115,140,1},
            {1130,255,100,1},
            {1230,285,200,1},
            {570,70,100,1},
            {585,160,80,1},
            {670,120,80,1},
            {910, 330, 160, 1}
        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }

    public void establecerRectableNivel5() {

        int[][] datos = new int[][]{{0, 0,Gdx.graphics.getWidth(), 30}, // suelo
            {0, 0, 1, Gdx.graphics.getHeight()}, // pared izq
            {Gdx.graphics.getWidth()-5, 0, 1, Gdx.graphics.getHeight()},// pared der
            {240, 320, 290, 1}, // 1
            {285, 200, 135, 1}, // 2
            {640, 115, 440, 1}, // 3
            {520, 200, 240, 1}, // 4
            {0, 320, 150, 1}, // 5
            {680, 290, 130, 1}, // 6
            {810, 235, 90, 1}, // 7
            {1280, 200, 100, 1}, // 8
            {1280, 380, 100, 1}, // 9
        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }

    public void establecerRectableNivel6() {

        int[][] datos = new int[][]{{0, 0,Gdx.graphics.getWidth(), 60}, // suelo
            {0, 0, 1, Gdx.graphics.getHeight()}, // pared izq
            {Gdx.graphics.getWidth()-5, 0, 1, Gdx.graphics.getHeight()},// pared der
            {0,460,210,1},
            {0,320,60,1},
            {305,180,395,1},
            {305,320,105,1},
            {905,200,500,1},
            {70,190,50,1}
        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }
    }


    public void finJuego() {
        int[][] datos = new int[][]{
            {620,340,170,50},
            {620,230,170,50}
        };

        superficies = new Array<>();
        for (int i = 0; i < datos.length; i++) {
            superficies.add(new Rectangle(datos[i][0], datos[i][1], datos[i][2], datos[i][3]));

        }




    }
}

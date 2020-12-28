/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversorimagem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author g4l1l3u
 */
public class TelaInicialController implements Initializable {

    
    @FXML
    private Button btAbrir;
    @FXML
    private Button btLimpar;
    @FXML
    private ImageView imgVOriginal;
    @FXML
    private ImageView imgVAlterado;
    @FXML
    private Label lbOriinal;
    @FXML
    private Label lbAlterado;
    @FXML
    private Button btHSI;
    @FXML
    private Button btRGB;
    @FXML
    private SplitPane splPImgView;
    @FXML
    private Button btIntBMais;
    @FXML
    private Button btIntBMenos;
    @FXML
    private Label lbntBrilho;
    @FXML
    private Label lbHUE;
    @FXML
    private Button btHUEMais;
    @FXML
    private Button btHUEMenos;
    @FXML
    private Label lbIntHUE;
    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;
    @FXML
    private Pane pnContBotoes;
    @FXML
    private Pane pnBtConv;
    @FXML
    private Label lbR;
    @FXML
    private Label lbG;
    @FXML
    private Label lbB;
    @FXML
    private Label lbC;
    @FXML
    private Label lbM;
    @FXML
    private Label lbY;
    @FXML
    private Label lbH;
    @FXML
    private Label lbS;
    @FXML
    private Label lbI;
    @FXML
    private Label lbXY;
    @FXML
    private Pane pnCoordenadas;
    @FXML
    private AnchorPane acPImgOriginal;
    @FXML
    private AnchorPane acPImgAlterado;
    @FXML
    private Button btLuminancia;
    @FXML
    private Label lbV1;
    @FXML
    private Label lbV2;
    @FXML
    private Label lbV3;
    @FXML
    private Pane pnLbMini;
    @FXML
    private Button btCMY;
    
    private FileChooser fileChooser;
    private File file;
    BufferedImage bimg, bimgH, bimgS, bimgI;
    private Color rgb;
    private int width, heigth, brilho, hue;
    
    private int[][] r;
    private int[][] g;
    private int[][] b;
    
    private int[][] h;
    private int[][] s;
    private int[][] i;
    
    private int[][] c;
    private int[][] m;
    private int[][] y;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bimg = null;
        bimgH = null; 
        bimgS = null; 
        bimgI = null;
        rgb = null;
        fileChooser = new FileChooser();
        brilho = 0;
        hue = 0;
        pnContBotoes.setDisable(true);
        pnBtConv.setDisable(true);
        pnCoordenadas.setDisable(true);
        pnCoordenadas.setVisible(false);
        pnLbMini.setVisible(false);
    }

    @FXML
    private void clkAbrir(ActionEvent event) throws FileNotFoundException, IOException {
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivos de Imagem", "*.jpg", "*.gif", "*.bmp", "*.png"));
        file = fileChooser.showOpenDialog(btAbrir.getParent().getScene().getWindow());
        if (file != null) {

            Image img = new Image(file.toURI().toString());
            imgVOriginal.setImage(img);
            carregaMatrizes();
            pnBtConv.setDisable(false);
            pnCoordenadas.setDisable(false);
        }

    }

    private void carregaMatrizes() {

        bimg = SwingFXUtils.fromFXImage(imgVOriginal.getImage(), null);
        bimgH = SwingFXUtils.fromFXImage(imgVOriginal.getImage(), null);
        bimgS = SwingFXUtils.fromFXImage(imgVOriginal.getImage(), null);
        bimgI = SwingFXUtils.fromFXImage(imgVOriginal.getImage(), null);
        
        width = bimg.getWidth();
        heigth = bimg.getHeight();

        r = new int[width][heigth];
        g = new int[width][heigth];
        b = new int[width][heigth];
        
        h = new int[width][heigth];
        s = new int[width][heigth];
        i = new int[width][heigth];
        
        c = new int[width][heigth];
        m = new int[width][heigth];
        y = new int[width][heigth];
        
        int[] vethsi = new int[3];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                rgb = new Color(bimg.getRGB(i, j));
                //Inicializa a matriz de RGB
                r[i][j] = rgb.getRed();
                g[i][j] = rgb.getGreen();
                b[i][j] = rgb.getBlue();
                
                //Inicializa a matriz de HSI, a partir da matriz RGB ja preenchida
                vethsi = normaliza_HSI(r[i][j], g[i][j], b[i][j]);
                h[i][j] = vethsi[0];
                s[i][j] = vethsi[1];
                this.i[i][j] = vethsi[2];
                
                //Inicializa a matriz CMY, a partir da matriz de RGB ja preenchida
                c[i][j] = 255 - r[i][j];
                m[i][j] = 255 - g[i][j];
                y[i][j] = 255 - b[i][j];
                
            }
        }
    }

    @FXML
    private void clkLimpar(ActionEvent event) {
        
        brilho = 0;
        hue = 0;
        
        imgVOriginal.setImage(null);
        imgVAlterado.setImage(null);
        
        imgV1.setImage(null);
        imgV2.setImage(null);
        imgV3.setImage(null);
        
        pnContBotoes.setDisable(true);
        pnBtConv.setDisable(true);
        pnCoordenadas.setDisable(true);
        pnCoordenadas.setVisible(false);
        pnLbMini.setVisible(false);
        
        r = new int[width][heigth];
        g = new int[width][heigth];
        b = new int[width][heigth];
        
        h = new int[width][heigth];
        s = new int[width][heigth];
        i = new int[width][heigth];
        
        c = new int[width][heigth];
        m = new int[width][heigth];
        y = new int[width][heigth];
    }
    
    public void limpa()
    {
        imgV1.setImage(null);
        imgV2.setImage(null);
        imgV3.setImage(null);
    }
    
    public void AttLabelHSI()
    {
        lbV1.setText("H");
        lbV2.setText("S");
        lbV3.setText("I");
        pnLbMini.setVisible(true);
    }

    private void convert_HSI() {

        pnContBotoes.setDisable(false);
        
        AttLabelHSI();
        
        int vet[] = new int[3];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {

                vet[0] = this.h[i][j]; 
                vet[1] = this.s[i][j];
                vet[2] = this.i[i][j];

                imgVA(vet, i, j);
                imgV1(vet[0], i, j);
                imgV2(vet[1], i, j);
                imgV3(vet[2], i, j);
            }
        }
        AttimgVA();
        AttimgV1();
        AttimgV2();
        AttimgV3();
        
    }

    public int[] normaliza_HSI(int R, int G, int B) {
        double r, g, b, cor, var, var2;
        double h = 0, s, i;
        int H, S, I;
        int vet[] = new int[3];

        cor = R + G + B;
        if (cor == 0) {
            r = g = b = 0;
        } else {
            r = R / cor;
            g = G / cor;
            b = B / cor;
        }
        var = Math.sqrt((Math.pow(r - g, 2)) + ((r - b) * (g - b)));
        if (var == 0) {
            var = 1;
        }
        var2 = (0.5 * ((r - g) + (r - b)));

        if (b <= g) {
            h = Math.acos(var2 / var);
        } else if (b > g) {
            h = 2 * Math.PI - Math.acos(var2 / var);
        }

        s = 1 - 3 * Math.min(r, Math.min(g, b));
        i = cor / (3 * 255);

        H = (int) ((h * 180) / Math.PI);
        S = (int) (s * 100);
        I = (int) (i * 255);

        if (H < 0) {
            H = 0;
        }
        if (H > 255) {
            H = 255;
        }
        if (S < 0) {
            S = 0;
        }
        if (S > 255) {
            S = 255;
        }
        if (I < 0) {
            I = 0;
        }
        if (I > 255) {
            I = 255;
        }

        vet[0] = H;
        vet[1] = S;
        vet[2] = I;
        

        return vet;
    }

    public void convert_RGB() {

        pnContBotoes.setDisable(false);
        lbV1.setText("R");
        lbV2.setText("G");
        lbV3.setText("B");
        pnLbMini.setVisible(true);
        
        int vet[];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                
                vet = normaliza_RGB(this.h[i][j], this.s[i][j], this.i[i][j]);
                
                imgVA(vet, i, j);
                imgV1(vet[0], i, j);
                imgV2(vet[1], i, j);
                imgV3(vet[2], i, j);
            }
        }
        
        AttimgVA();
        AttimgV1();
        AttimgV2();
        AttimgV3();
    }

    public int[] normaliza_RGB(int H, int S, int I) {
        double h, s, i;
        double x, y, z;
        int[] vet = new int[3];

        h = H * Math.PI / 180.0;
        s = S / 100.0;
        i = I / 255.0;

        if (h < 2 * Math.PI / 3) {
            x = i * (1 - s);
            y = i * (1 + ((s * Math.cos(h)) / (Math.cos(Math.PI / 3 - h))));
            z = 3 * i - (x + y);

            vet[0] = (int) (y * 255);
            vet[1] = (int) (z * 255);
            vet[2] = (int) (x * 255);
        } else {
            if (2 * Math.PI / 3 <= h && h < 4 * Math.PI / 3) {
                h = h - 2 * Math.PI / 3;

                x = i * (1 - s);
                y = i * (1 + ((s * Math.cos(h)) / (Math.cos(Math.PI / 3 - h))));
                z = 3 * i - (x + y);

                vet[0] = (int) (x * 255);
                vet[1] = (int) (y * 255);
                vet[2] = (int) (z * 255);
            } else {
                h = h - 4 * Math.PI / 3;

                x = i * (1 - s);
                y = i * (1 + ((s * Math.cos(h)) / (Math.cos(Math.PI / 3 - h))));
                z = 3 * i - (x + y);

                vet[0] = (int) (z * 255);
                vet[1] = (int) (x * 255);
                vet[2] = (int) (y * 255);
            }
        }
        if (vet[0] < 0) {
            vet[0] = 0;
        }
        if (vet[0] > 255) {
            vet[0] = 255;
        }
        if (vet[1] < 0) {
            vet[1] = 0;
        }
        if (vet[1] > 255) {
            vet[1] = 255;
        }
        if (vet[2] < 0) {
            vet[2] = 0;
        }
        if (vet[2] > 255) {
            vet[2] = 255;
        }
        return vet;
    }
    
    public void imgVA(int[] vet, int i, int j)
    {
        rgb = new Color(vet[0], vet[1], vet[2]);
        bimg.setRGB(i, j, rgb.getRGB());
    }
    
    public void AttimgVA()
    {
        Image nv = SwingFXUtils.toFXImage(bimg, null);
        imgVAlterado.setImage(nv);
    }
    
    public void imgV1(int h, int i, int j)
    {
        rgb = new Color(h, h, h);
        bimgH.setRGB(i, j, rgb.getRGB());
    }
    
    public void AttimgV1()
    {
        Image nv = SwingFXUtils.toFXImage(bimgH, null);
        imgV1.setImage(nv);
    }
    
    public void imgV2(int s, int i, int j)
    {
        rgb = new Color(s, s, s);
        bimgS.setRGB(i, j, rgb.getRGB());
    }
    
    public void AttimgV2()
    {
        Image nv = SwingFXUtils.toFXImage(bimgS, null);
        imgV2.setImage(nv);
    }
    
    public void imgV3(int ii, int i, int j)
    {
        rgb = new Color(ii, ii, ii);
        bimgI.setRGB(i, j, rgb.getRGB());
    }
    
    public void AttimgV3()
    {
        Image nv = SwingFXUtils.toFXImage(bimgI, null);
        imgV3.setImage(nv);
    }
    

    @FXML
    private void clkHSI(ActionEvent event) {
        convert_HSI();
    }

    @FXML
    private void clkRGB(ActionEvent event) {
        convert_RGB();
    }
    
    public void intesidade_Brilho()
    {
        int h, s, ii;
        int[] vet;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {

                
                h = this.h[i][j];
                s = this.s[i][j];
                ii = this.i[i][j] + brilho;
                
                if(ii > 255)
                    ii = 255;
                if(ii < 0)
                    ii = 0;
                
                vet = normaliza_RGB(h, s, ii);
                
                imgVA(vet, i, j);
                imgV1(h, i, j);
                imgV2(s, i, j);
                imgV3(ii, i, j);

            }
        }
        AttimgVA();
        AttimgV1();
        AttimgV2();
        AttimgV3();
    }

    @FXML
    private void clkBtBrilhoMais(ActionEvent event) {
        AttLabelHSI();
        brilho += 20;
        intesidade_Brilho();
        //lbntBrilho.setText(String.valueOf(brilho));
    }

    @FXML
    private void clkBtBrilhoMenos(ActionEvent event) {
        AttLabelHSI();
        brilho -= 20;
        intesidade_Brilho();
        //lbntBrilho.setText(String.valueOf(brilho));
    }
    
    public void HUE()
    {
        int h, s, ii;
        int[] vet;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {

                
                h = this.h[i][j] + hue;
                s = this.s[i][j];
                ii = this.i[i][j];
                
                if(h > 360)
                    h %= hue;
                
                if(h > 255)
                    h = 255;
                if(h < 0)
                   h = 0;
                
                vet = normaliza_RGB(h, s, ii);
                
                imgVA(vet, i, j);
                imgV1(h, i, j);
                imgV2(s, i, j);
                imgV3(ii, i, j);

            }
        }
        AttimgVA();
        AttimgV1();
        AttimgV2();
        AttimgV3();
    }

    @FXML
    private void clkBtHUEMais(ActionEvent event) {
        AttLabelHSI();
        hue += 10;
        HUE();
    }

    @FXML
    private void clkBtHUEMenos(ActionEvent event) {
        AttLabelHSI();
        hue -= 10;
        HUE();
    }

    @FXML
    private void coordenadas(MouseEvent event) {
        
        if(!pnCoordenadas.isDisable())
        {
            pnCoordenadas.setVisible(true);
            int x = (int)event.getX(), y = (int)event.getY();
            lbR.setText("R:"+r[x][y]);
            lbG.setText("G:"+g[x][y]);
            lbB.setText("B:"+b[x][y]);

            lbC.setText("C:"+c[x][y]);
            lbM.setText("M:"+m[x][y]);
            lbY.setText("Y:"+this.y[x][y]);

            lbH.setText("H:"+h[x][y]);
            lbS.setText("S:"+s[x][y]);
            lbI.setText("I:"+i[x][y]);

            lbXY.setText("(X:"+x+", Y:"+y+")");
        }
        
    }
    
    public void convert_Luminancia() {

        pnContBotoes.setDisable(false);
        
        int vet[] = new int[3];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {

                vet[0] = (int)(0.299 * r[i][j] + 0.587 * g[i][j] + 0.114 * b[i][j]);
                vet[1] = vet[2] = vet[0];
                imgVA(vet, i, j);
            }
        }
        AttimgVA();
    }

    @FXML
    private void clkBtLuminancia(ActionEvent event) {
        imgV1.setImage(null);
        imgV2.setImage(null);
        imgV3.setImage(null);
        pnLbMini.setVisible(false);
        convert_Luminancia();
    }
    
    private void convert_CMY()
    {
        pnContBotoes.setDisable(false);
        lbV1.setText("C");
        lbV2.setText("M");
        lbV3.setText("Y");
        pnLbMini.setVisible(true);
        
        int vet[] = new int[3];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {

                vet[0] = this.c[i][j]; 
                vet[1] = this.m[i][j];
                vet[2] = this.y[i][j];
                
                imgVA(vet, i, j);
                
                imgV1(vet[0], i, j);
                imgV2(vet[1], i, j);
                imgV3(vet[2], i, j);
            }
        }
        AttimgVA();
        AttimgV1();
        AttimgV2();
        AttimgV3();
        
    }

    @FXML
    private void clkBtCMY(ActionEvent event) {
        convert_CMY();
    }

}

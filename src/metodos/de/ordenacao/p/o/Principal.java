/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.de.ordenacao.p.o;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Principal
{

    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    private int tini, tfim, movO, compO, movR, compR, movRev, compRev, ttotalO, ttotalRev, ttotalRand, compRand, movRand;
    private int ttotalR;

    public Principal()
    {

        arqOrd = new Arquivo("Ordenado.dat");
        arqRev = new Arquivo("Reverso.dat");
        arqRand = new Arquivo("Random.dat");
        auxRev = new Arquivo("auxRev.dat");
        auxRand = new Arquivo("auxRand.dat");

    }

    public void gravaLinha(int compP, int compE, int movP, int movE, int tempo)
    {
        System.out.println("\t\t|" + compP + "\t\t|" + compE + "\t\t|" + movP + "\t\t|" + movE + "\t\t|" + tempo + "\t\t|");
    }

    public void insercaoDireta() throws IOException
    {
        //Arquivo Ordenado
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;
        System.out.println("Arquivo Ordenado");
        gravaLinha(compO, (int) ((Math.pow((int) arqOrd.filesize(), 2) - (int) arqOrd.filesize()) / 2), movO, (int) (arqOrd.filesize() * (Math.log((double) arqOrd.filesize()) + 0.577216f)), ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        System.out.println("Arquivo Reverso");
        gravaLinha(compRev, (int) ((Math.pow((int) auxRev.filesize(), 2) - (int) auxRev.filesize()) / 2), movRev, (int) (auxRev.filesize() * (Math.log((double) auxRev.filesize()) + 0.577216f)), ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        System.out.println("Arquivo Randomico");
        gravaLinha(compRand, (int) ((Math.pow((int) auxRand.filesize(), 2) - (int) auxRand.filesize()) / 2), movRand, (int) (auxRand.filesize() * (Math.log((double) auxRand.filesize()) + 0.577216f)), ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos

    }

    public void geraTabela() throws IOException
    {
        arqOrd.geraArquivoOrdenado();
        arqRev.geraArquivoReverso();
        arqRand.geraArquivoRandomico();
        insercaoDireta();

        //e assim continua para os outros métodos de ordenacao!!!
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        // TODO code application logic here
        Principal p = new Principal();
        p.geraTabela();
    }

}

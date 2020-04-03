/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.de.ordenacao.p.o;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Gabriel
 */
public class Principal
{

    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    private int tini, tfim, movO, compO, movR, compR, movRev, compRev, ttotalO, ttotalRev, ttotalRand, compRand, movRand;
    private Lista lOrd, lRand, lRev;
    FileWriter fw;
    PrintWriter pw;

    public Principal()
    {
        arqOrd = new Arquivo("Ordenado.dat");
        arqRev = new Arquivo("Reverso.dat");
        arqRand = new Arquivo("Random.dat");
        auxRev = new Arquivo("auxRev.dat");
        auxRand = new Arquivo("auxRand.dat");

        try
        {
            fw = new FileWriter("tabela.txt");
            pw = new PrintWriter(fw);

        } catch (IOException e)
        {
            System.out.println("Erro ao criar arquivo TXT");
            System.exit(-1);
        }
    }

    public void geraCabecalho()
    {
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        pw.print("|   Metodos Ordenação\t|");
        pw.println("   Comp. Prog\t|   Comp. Equa\t|   Mov. Prog\t|    Mov. Equa\t|    Tempo\t|"); // INICIO DA TABELA
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    //comparacao programa, comparacao esperada, movimentacao programa, movimentacao esperada, tempo total
    public void gravaLinha(int compP, int compE, int movP, int movE, int tempo) throws IOException
    {
        pw.println("\t|" + compP + "\t\t|" + compE + "\t\t|" + movP + "\t\t|" + movE + "\t\t|" + tempo + "\t\t|");
    }

    public void insercaoDireta() throws IOException // CERTO
    {
        //Arquivo Ordenado       
        pw.println("|   Inserção Direta\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO        
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, (int) arqOrd.filesize() - 1, movO, (int) (3 * (arqOrd.filesize() - 1)), ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, (int) ((Math.pow((int) auxRev.filesize(), 2) + (int) auxRev.filesize()) - 4) / 4, movRev, (int) ((Math.pow((int) auxRev.filesize(), 2) + 3 * auxRev.filesize() - 4) / 2), ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.InsercaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, (int) ((Math.pow((int) auxRand.filesize(), 2) + (int) auxRand.filesize() - 2) / 4), movRand, (int) ((Math.pow((int) auxRand.filesize(), 2) + 9 * auxRand.filesize() - 10) / 4), ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void InsercaoBinaria() throws IOException // CERTO
    {
        pw.println("\n|   Inserção Binaria\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.InsercaoBinariaArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, (int) (arqOrd.filesize() * (Math.log10(arqOrd.filesize()) - Math.log10(Math.E) + 0.5)), movO, (int) (3 * (arqOrd.filesize() - 1)), ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.InsercaoBinariaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, (int) (auxRev.filesize() * (Math.log10(auxRev.filesize()) - Math.log10(Math.E) + 0.5)), movRev, (int) ((Math.pow(auxRev.filesize(), 2) + 9 * auxRev.filesize() - 10) / 4), ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.InsercaoBinariaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, (int) (auxRand.filesize() * (Math.log10(auxRand.filesize()) - Math.log10(Math.E) + 0.5)), movRand, (int) ((Math.pow((int) auxRand.filesize(), 2) + 3 * auxRand.filesize() - 4) / 2), ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void BubbleSort() throws IOException // CERTO
    {
        pw.println("\n|   Bubble Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.bubbleSortArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, (int) (Math.pow(arqOrd.filesize(), 2) - arqOrd.filesize()) / 2, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.bubbleSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, (int) (Math.pow(auxRev.filesize(), 2) - auxRev.filesize()) / 2, movRev, (int) (3 * (Math.pow((int) auxRev.filesize(), 2) - auxRev.filesize())) / 4, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.bubbleSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, (int) ((Math.pow(auxRand.filesize(), 2) - auxRand.filesize()) / 2), movRand, (int) (3 * (Math.pow((int) auxRand.filesize(), 2) - auxRand.filesize())) / 2, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void SelecaoDireta() throws IOException // CERTO
    {
        pw.println("\n|   Seleção Direta\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.SelecaoDiretaArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, (int) ((Math.pow(arqOrd.filesize(), 2) - arqOrd.filesize()) / 2), movO, (int) (3 * (arqOrd.filesize() - 1)), ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.SelecaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, (int) ((Math.pow(auxRev.filesize(), 2) - auxRev.filesize()) / 2), movRev, (int) (Math.pow((int) auxRev.filesize(), 2) / 4 + 3 * (auxRev.filesize() - 1)), ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.SelecaoDiretaArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, (int) ((Math.pow(arqRand.filesize(), 2) - auxRand.filesize()) / 2), movRand, (int) (auxRand.filesize() * Math.log10(auxRand.filesize()) + 0.577216), ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos          
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void Heap() throws IOException
    {
        pw.println("\n|   Heap Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.heapArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos

        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.heapArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.heapArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void Shell() throws IOException
    {
        pw.println("\n|   Shell Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.ShellArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.ShellArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.ShellArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");

    }

    public void GnomeSort() throws IOException
    {
        pw.println("\n|   Gnome Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.GnomeSortArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.GnomeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.GnomeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void CombSort() throws IOException
    {
        pw.println("\n|   Comb Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.CombSortArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.CombSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.CombSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void ShakeSort() throws IOException
    {
        pw.println("\n|   Shake Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.ShakeSortArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.ShakeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.ShakeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void QuickPivo() throws IOException
    {
        pw.println("|   Quick Pivot Sort\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.QuickComPivoArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.QuickComPivoArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.QuickComPivoArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");

    }

    public void QuickSort() throws IOException
    {
        pw.println("\n|   Quick Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.QuickSemPivoArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.QuickSemPivoArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.QuickSemPivoArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void RadixSort() throws IOException
    {
        pw.println("\n|   Radix Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.Radix();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso        
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.Radix();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.Radix();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void MergeSortI() throws IOException
    {
        pw.println("\n|   Merge Sort\t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.MergeSortArq();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.MergeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.MergeSortArq();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void MergeSortII() throws IOException
    {
        pw.println("\n|   Merge Sort II\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.mergeSortArqII();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.mergeSortArqII();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();

        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.mergeSortArqII();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void CountingSort() throws IOException
    {
        pw.println("\n|   Counting Sort \t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.CountingSort();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;

        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.CountingSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.CountingSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");

    }

    public void BucketSort() throws IOException
    {
        pw.println("\n|   Bucket Sort \t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.BucketSort();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;
        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.BucketSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.BucketSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
    }

    public void TimSort() throws IOException
    {
        pw.println("\n|   Tim Sort \t\t|\t\t\t\t\t\t\t\t\t\t|"); // NOME METODO
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        arqOrd.initComp();
        arqOrd.initMov();
        tini = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.TimSort();
        tfim = (int) System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO = arqOrd.getComp();
        movO = arqOrd.getMov();
        ttotalO = tfim - tini;
        pw.print("|   Arquivo Ordenado");
        gravaLinha(compO, 0, movO, 0, ttotalO);//tempo execução no Arquivo Ordenado já convertido para segundos
        //Arquivo Reverso
        arqRev.seekArq(0);
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini = (int) System.currentTimeMillis();
        auxRev.TimSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRev = tfim - tini;
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        pw.print("|   Arquivo Reverso");
        gravaLinha(compRev, 0, movRev, 0, ttotalRev);//tempo execução no Arquivo Reverso já convertido para segundos

        //Arquivo Randomico
        arqRand.seekArq(0);
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini = (int) System.currentTimeMillis();
        auxRand.TimSort();
        tfim = (int) System.currentTimeMillis();
        ttotalRand = tfim - tini;
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        pw.print("|   Arquivo Randomico");
        gravaLinha(compRand, 0, movRand, 0, ttotalRand);//tempo execução no Arquivo Randomico já convertido para segundos   
        pw.print("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        
    }
    public void geraRodape() throws IOException
    {
        pw.println("|\tRA: 101619427 Gabriel Marioto \t\t\t| \tRA: 101730179 Bruno Mattos\t\t|");
        pw.println("+-----------------------+---------------+---------------+---------------+---------------+---------------+");
        fw.close();
    }

    public void geraTabela() throws IOException
    {
        arqOrd.geraArquivoOrdenado();
        arqRev.geraArquivoReverso();
        arqRand.geraArquivoRandomico();

        geraCabecalho();

        insercaoDireta();
        System.out.println("Direta");
        InsercaoBinaria();
        System.out.println("Binaria");
        BubbleSort();
        System.out.println("Bolha");
        Heap();
        System.out.println("Heap");
        Shell();
        System.out.println("Shell");
        GnomeSort();
        System.out.println("Gnome");
        CombSort();
        System.out.println("Comb");
        ShakeSort();
        System.out.println("Shake");
        QuickSort();
        System.out.println("Quick I");
        QuickPivo();
        System.out.println("Quick II");
        RadixSort();
        System.out.println("Radix");
        MergeSortI();
        System.out.println("Merge I");
        MergeSortII();
        System.out.println("Merge II");
        CountingSort();
        System.out.println("Counting Sort");
        BucketSort();
        System.out.println("Bucket Sort");
        TimSort();
        System.out.println("Tim Sort");
        SelecaoDireta();
        System.out.println("Direta");
        

        geraRodape();
        
//        System.out.println("Ordenado: "+arqOrd.toString());
//        System.out.println("Reverso: "+auxRev.toString());
//        System.out.println("Random: "+auxRand.toString());
    }

    public void initLista()
    {
        lOrd = new Lista();
        lRand = new Lista();
        lRev = new Lista();
    }

    public void MetodosLista()
    {
        initLista();
        lOrd.geraListaOrdenada();
        lRand.geraListaRandomica();
        lRev.geraListaReversa();

//        lOrd.InsercaoDiretaLista();
//        lRand.InsercaoDiretaLista();
//        lRev.InsercaoDiretaLista();
//
//        lOrd.selecaoDiretaLista();
//        lRand.selecaoDiretaLista();
//        lRev.selecaoDiretaLista();      
//        
//        lOrd.BubbleSortLista();
//        lRand.BubbleSortLista();
//        lRev.BubbleSortLista();      
//        
//        lOrd.ShakeSortLista();
//        lRand.ShakeSortLista();
//        lRev.ShakeSortLista();
//         
//        lOrd.QuickSemPivo();
//        lRand.QuickSemPivo();
//        lRev.QuickSemPivo();
//             
//        lOrd.insercaoBinariaLista();
//        lRand.insercaoBinariaLista();
//        lRev.insercaoBinariaLista();           
//        
//        lOrd.ShellLista();
//        lRand.ShellLista();
//        lRev.ShellLista();        
//        
//        lOrd.QuickComPivo();
//        lRand.QuickComPivo();
//        lRev.QuickComPivo();
//        
//        lOrd.mergeListaI();
//        lRand.mergeListaI();
//        lRev.mergeListaI();
//        
//        lOrd.mergeSortListaII();
//        lRand.mergeSortListaII();
//        lRev.mergeSortListaII();
//
//        lOrd.heap();
//        lRand.heap();
//        lRev.heap();
//
//        lOrd.radixSort();
//        lRand.radixSort();
//        lRev.radixSort();
//        
//        lOrd.bucketSort();
//        lRand.bucketSort();
//        lRev.bucketSort();
//        
//        lOrd.combSort();
//        lRand.combSort();
//        lRev.combSort();
//        
//        lOrd.gnomeSort();
//        lRand.gnomeSort();
//        lRev.gnomeSort();

//        lOrd.TimSort();
//        lRand.TimSort();
//        lRev.TimSort();        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        // TODO code application logic here
        
        //A tabela está feita corretamente.
        //Caso ocorre erro na hora da execução, apagar os arquivos: Ordenado, Randomico, Reverso, auxRev e auxRand, pois as vezes o tamanho deles acabam bugando.
        Principal p = new Principal();
        p.geraTabela();
//        p.MetodosLista();
    }

}

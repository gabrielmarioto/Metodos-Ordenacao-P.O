/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.de.ordenacao.p.o;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 *
 * @author Gabriel
 */
public class Arquivo
{

    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo(String nomearquivo)
    {
        try
        {
            this.nomearquivo = nomearquivo;
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (FileNotFoundException e)
        {
        }
    }

    public void copiaArquivo(RandomAccessFile arquivoOrigem)
    {
        Registro r = new Registro();
        while (!eof())
        {
            r.leDoArq(arquivoOrigem);
            r.gravaNoArq(arquivo);
        }
    }

    public RandomAccessFile getFile()
    {
        return arquivo;
    }

    public void truncate(long pos)
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        {
        }
    }

    public boolean eof(RandomAccessFile arquivoOrigem)
    {
        boolean retorno = false;
        try
        {
            if (arquivoOrigem.getFilePointer() == arquivoOrigem.length())
                retorno = true;
        } catch (IOException e)
        {
        }
        return (retorno);
    }

    public boolean eof()
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e)
        {
        }
        return (retorno);
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        {
        }
    }

    public long filesize() throws IOException
    {
        return (int) (arquivo.length() / Registro.length());
    }

    public void initComp()
    {
        comp = 0;
    }

    public void initMov()
    {
        mov = 0;
    }

    public int getComp()
    {
        return comp;
    }

    public int getMov()
    {
        return mov;
    }

    public void geraArquivoOrdenado()
    {
//        for (int i = 0; i < 1024; i++)
        for (int i = 0; i < 10; i++)
            new Registro(i).gravaNoArq(arquivo);
    }

    public void geraArquivoReverso()
    {
//        for (int i = 1023; i >= 0; i--)
        for (int i = 10; i >= 0; i--)
            new Registro(i).gravaNoArq(arquivo);
    }

    public void geraArquivoRandomico()
    {
        Random random = new Random();
//        for (int i = 0; i < 1024; i++)
        for (int i = 0; i < 10; i++)
            new Registro(random.nextInt(1000000)).gravaNoArq(arquivo);
    }

    public int buscaBinariaArq(int chave, int tl)
    {
        Registro reg1 = new Registro();
        int inicio = 0, fim = tl - 1, meio = tl / 2;

        seekArq(meio);
        reg1.leDoArq(arquivo);

        while (inicio < fim && reg1.getNumero() != chave)
        {
            if (reg1.getNumero() == chave)
                return meio;
            else
                if (chave > reg1.getNumero())
                    inicio = meio + 1;
                else
                    fim = meio - 1;

            meio = (inicio + fim) / 2;

            seekArq(meio);
            reg1.leDoArq(arquivo);
        }
        if (chave > reg1.getNumero())
            return meio + 1;
        return meio;
    }

    public void InsercaoBinariaArq() throws IOException
    {
        Registro aux = new Registro();
        Registro reg1 = new Registro();
        int pos, j;
        int tl = (int) filesize();

        for (int i = 1; i < tl; i++)
        {
            seekArq(i);
            aux.leDoArq(arquivo);
            pos = buscaBinariaArq(aux.getNumero(), i);
            for (j = i; j > pos; j--)
            {
                seekArq(j - 1);
                reg1.leDoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
            }
            seekArq(pos);
            aux.gravaNoArq(arquivo);
        }
    }

    public void InsercaoDiretaArq() throws IOException
    {
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        int i = 1, pos;
        int tl = (int) filesize();
        while (i < tl)
        {
            pos = i;
            seekArq(i);
            reg2.leDoArq(arquivo);
            seekArq(i - 1);
            reg1.leDoArq(arquivo);
            while (pos > 0 && reg2.getNumero() < reg1.getNumero())
            {
                seekArq(pos);
                reg1.gravaNoArq(arquivo);
                pos--;
                seekArq(pos - 1);
                reg1.leDoArq(arquivo);
                comp++;
                comp++;
                mov++;
            }
            i++;
            seekArq(pos);
            reg2.gravaNoArq(arquivo);
            mov++;
        }
    }

    public void bubbleSortArq() throws IOException
    {
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        int tl = (int) filesize();
        while (tl > 1)
        {
            for (int i = 0; i < tl - 1; i++)
            {
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if (reg1.getNumero() > reg2.getNumero())
                {
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                }
            }
            tl--;
        }
    }

    public void SelecaoDiretaArq() throws IOException
    {
        int i = 0, j;
        Registro menor = new Registro();
        Registro aux = new Registro();
        Registro num = new Registro();
        int posmenor = 0;

        int tl = (int) filesize();

        while (i < tl - 1)
        {
            seekArq(i);
            menor.leDoArq(arquivo);
            seekArq(i);
            num.leDoArq(arquivo);
            j = i + 1;
            while (j < tl)
            {
                seekArq(j);
                aux.leDoArq(arquivo);
                if (aux.getNumero() < menor.getNumero())
                {
                    seekArq(j);
                    menor.leDoArq(arquivo);
                    posmenor = j;
                }
                j++;
            }
            seekArq(i);
            menor.gravaNoArq(arquivo);
            seekArq(posmenor);
            num.gravaNoArq(arquivo);
            i++;
        }
    }

    public void heapArq() throws IOException
    {
        Registro reg1 = new Registro(), fe = new Registro(), fd = new Registro(), aux = new Registro(), maiorF = new Registro(), aux1 = new Registro();
        int pai, posMaiorF;

        int tl = (int) filesize();

        while (tl > 1)
        {
            pai = tl / 2 - 1;
            seekArq(pai);
            aux.leDoArq(arquivo);

            for (; pai >= 0; pai--)
            {
                seekArq(pai);
                reg1.leDoArq(arquivo);
                seekArq(pai + pai + 1);
                fe.leDoArq(arquivo);
                seekArq(pai + pai + 2);
                fd.leDoArq(arquivo);
                if (pai + pai + 2 < tl && fd.getNumero() > fe.getNumero())
                {
                    seekArq(pai + pai + 2);
                    maiorF.leDoArq(arquivo);
                    posMaiorF = pai + pai + 2;
                } else
                {
                    seekArq(pai + pai + 1);
                    maiorF.leDoArq(arquivo);
                    posMaiorF = pai + pai + 1;
                }

                if (maiorF.getNumero() > reg1.getNumero())
                {
                    seekArq(pai);
                    maiorF.gravaNoArq(arquivo);
                    seekArq(posMaiorF);
                    reg1.gravaNoArq(arquivo);
                }
            }
            seekArq(0);
            aux.leDoArq(arquivo);
            seekArq(tl - 1);
            aux1.leDoArq(arquivo);
            seekArq(tl - 1);
            aux.gravaNoArq(arquivo);
            seekArq(0);
            aux1.gravaNoArq(arquivo);
            tl--;
        }
    }

    public void ShellArq() throws IOException
    {
        int j, k, i, dist = 4;
        Registro reg1 = new Registro(), reg2 = new Registro();
        Registro auxK = new Registro();
        int tl = (int) filesize();

        while (dist > 0)
        {
            for (i = 0; i < dist; i++)
            {
                j = i;
                while (j + dist < tl)
                {
                    seekArq(j);
                    reg1.leDoArq(arquivo);
                    seekArq(j + dist);
                    reg2.leDoArq(arquivo);
                    if (reg1.getNumero() > reg2.getNumero())
                    {
                        seekArq(j + dist);
                        reg1.gravaNoArq(arquivo);
                        seekArq(j);
                        reg2.gravaNoArq(arquivo);
                        k = j;
                        seekArq(k - dist);
                        auxK.leDoArq(arquivo);

                        while (k - dist >= i && reg2.getNumero() < auxK.getNumero())
                        {
                            seekArq(k - dist);
                            reg2.gravaNoArq(arquivo);
                            seekArq(k);
                            auxK.gravaNoArq(arquivo);
                            k = k - dist;
                            seekArq(k - dist);
                            auxK.leDoArq(arquivo);
                        }
                    }
                    j += dist;
                }
            }
            dist /= 2;
        }
    }
}

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
        seekArq(0);
        while (!eof(arquivoOrigem))
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
            {
                retorno = true;
            }
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
            {
                retorno = true;
            }
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
        for (int i = 0; i < 1024; i++) 
        {
            new Registro(i).gravaNoArq(arquivo);
        }
    }

    public void geraArquivoReverso()
    {
        for (int i = 1023; i >= 0; i--) 
        {
            new Registro(i).gravaNoArq(arquivo);
        }
    }

    public void geraArquivoRandomico()
    {
        Random random = new Random();
        for (int i = 0; i < 1024; i++) 
        {
            new Registro(random.nextInt(10)).gravaNoArq(arquivo);
        }
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
            {
                return meio;
            } else if (chave > reg1.getNumero())
            {
                inicio = meio + 1;
            } else
            {
                fim = meio - 1;
            }

            meio = (inicio + fim) / 2;

            seekArq(meio);
            reg1.leDoArq(arquivo);
        }
        if (chave > reg1.getNumero())
        {
            return meio + 1;
        }
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
            comp++;
            pos = buscaBinariaArq(aux.getNumero(), i);
            for (j = i; j > pos; j--)
            {
                seekArq(j - 1);
                reg1.leDoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
                comp++;
                mov++;
            }
            seekArq(pos);
            aux.gravaNoArq(arquivo);
            mov++;
        }
    }

    public int getMax()
    {
        Registro reg1 = new Registro();
        int max = 0;
        seekArq(0);
        reg1.leDoArq(arquivo);
        while (!eof(arquivo))
        {
            if (reg1.getNumero() > max)
            {
                max = reg1.getNumero();
            }
            reg1.leDoArq(arquivo);
        }
        if (reg1.getNumero() > max)
        {
            max = reg1.getNumero();
        }
        return max;
    }

    @Override
    public String toString()
    {
        Registro r = new Registro();
        seekArq(0);
        StringBuilder sb = new StringBuilder();
        while (!eof())
        {
            r.leDoArq(arquivo);
            sb.append(r.getNumero()).append("\n");
        }
        return sb.toString();
    }

    public void Radix() throws IOException
    {
        int m = getMax();

        for (int exp = 1; m / exp > 0; exp *= 10)
        {
            Count(m, exp);
        }
    }

    public void Count(int maior, int exp) throws IOException
    {
        Registro reg1 = new Registro();
        int[] count = new int[10];
        int[] aux = new int[maior + 1];
        int tl = (int) filesize();
        seekArq(0);
        for (int i = 0; i < tl; i++)
        {
            reg1.leDoArq(arquivo);
            count[(reg1.getNumero() / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++)
        {
            count[i] += count[i - 1];
        }
        seekArq(0);
        for (int i = 0; i < tl; i++)
        {
            reg1.leDoArq(arquivo);
            aux[count[(reg1.getNumero() / exp) % 10] - 1] = reg1.getNumero();
            count[(reg1.getNumero() / exp) % 10]--;
        }

        for (int i = 0; i < tl; i++)
        {
            seekArq(i);
            reg1.setNumero(aux[i]);
            reg1.gravaNoArq(arquivo);
            mov++;
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
            comp++;
            while (pos > 0 && reg2.getNumero() < reg1.getNumero())
            {
                seekArq(pos);
                reg1.gravaNoArq(arquivo);
                pos--;
                seekArq(pos - 1);
                reg1.leDoArq(arquivo);
                mov++;
                comp++;
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
                comp++;
                if (reg1.getNumero() > reg2.getNumero())
                {
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                    comp++;
                    mov++;
                    mov++;
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
                comp++;
                if (aux.getNumero() < menor.getNumero())
                {
                    seekArq(j);
                    menor.leDoArq(arquivo);
                    posmenor = j;
                    comp++;
                }
                j++;
            }
            seekArq(i);
            menor.gravaNoArq(arquivo);
            seekArq(posmenor);
            num.gravaNoArq(arquivo);
            i++;
            mov++;
            mov++;
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
                comp++;
                if (pai + pai + 2 < tl && fd.getNumero() > fe.getNumero())
                {
                    seekArq(pai + pai + 2);
                    maiorF.leDoArq(arquivo);
                    posMaiorF = pai + pai + 2;
                    comp++;
                } else
                {
                    seekArq(pai + pai + 1);
                    maiorF.leDoArq(arquivo);
                    posMaiorF = pai + pai + 1;
                }
                comp++;
                if (maiorF.getNumero() > reg1.getNumero())
                {
                    seekArq(pai);
                    maiorF.gravaNoArq(arquivo);
                    seekArq(posMaiorF);
                    reg1.gravaNoArq(arquivo);
                    mov++;
                    mov++;
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
            mov++;
            mov++;
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
                    comp++;
                    if (reg1.getNumero() > reg2.getNumero())
                    {
                        seekArq(j + dist);
                        reg1.gravaNoArq(arquivo);
                        seekArq(j);
                        reg2.gravaNoArq(arquivo);
                        k = j;
                        seekArq(k - dist);
                        auxK.leDoArq(arquivo);
                        mov++;
                        mov++;
                        comp++;
                        while (k - dist >= i && reg2.getNumero() < auxK.getNumero())
                        {
                            seekArq(k - dist);
                            reg2.gravaNoArq(arquivo);
                            seekArq(k);
                            auxK.gravaNoArq(arquivo);
                            k = k - dist;
                            seekArq(k - dist);
                            auxK.leDoArq(arquivo);
                            mov++;
                            mov++;
                            comp++;
                        }
                    }
                    j += dist;
                }
            }
            dist /= 2;
        }
    }

    public void GnomeSortArq() throws IOException
    {
        int i = 0;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        int tl = (int) filesize();
        while (i < tl)
        {
            if (i == 0)
            {
                i++;
            }
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(i - 1);
            reg2.leDoArq(arquivo);
            comp++;
            if (reg1.getNumero() >= reg2.getNumero())
            {
                i++;
                comp++;
            } else
            {
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(i - 1);
                reg1.gravaNoArq(arquivo);
                i--;
                mov += 2;
            }
        }
    }

    public void CombSortArq() throws IOException
    {
        int gap = (int) filesize();
        int j;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        while (gap >= 1)
        {
            j = 0;
            while (j + gap < filesize())
            {
                seekArq(j);
                reg1.leDoArq(arquivo);
                seekArq(j + gap);
                reg2.leDoArq(arquivo);
                comp++;
                if (reg1.getNumero() > reg2.getNumero())
                {
                    seekArq(j + gap);
                    reg1.gravaNoArq(arquivo);
                    seekArq(j);
                    reg2.gravaNoArq(arquivo);
                    comp++;
                    mov += 2;
                }
                j++;
            }
            gap /= 1.3;
        }
    }

    public void ShakeSortArq() throws IOException
    {
        int i = 0, j;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        int tl = (int) filesize();

        while (i < tl - 1)
        {
            for (j = i; j < tl - 1; j++)
            {
                seekArq(j);
                reg1.leDoArq(arquivo);
                seekArq(j + 1);
                reg2.leDoArq(arquivo);
                comp++;
                if (reg1.getNumero() > reg2.getNumero())
                {
                    seekArq(j + 1);
                    reg1.gravaNoArq(arquivo);
                    seekArq(j);
                    reg2.gravaNoArq(arquivo);
                    comp++;
                    mov += 2;
                }
            }
            for (j = j - 1; j > i; j--)
            {
                seekArq(j);
                reg1.leDoArq(arquivo);
                seekArq(j - 1);
                reg2.leDoArq(arquivo);
                comp++;
                if (reg1.getNumero() < reg2.getNumero())
                {
                    seekArq(j - 1);
                    reg1.gravaNoArq(arquivo);
                    seekArq(j);
                    reg2.gravaNoArq(arquivo);
                    comp++;
                    mov += 2;
                }
            }
            i++;
            tl--;
        }
    }

    public void QuickComPivoArq() throws IOException
    {
        int tl = (int) filesize();
        QuickCPArq(0, tl - 1);
    }

    public void QuickCPArq(int ini, int fim)
    {
        int i = ini, j = fim;
        Registro reg1 = new Registro(), reg2 = new Registro();
        Registro pivo = new Registro();
        seekArq((ini + fim) / 2);
        pivo.leDoArq(arquivo);
        seekArq(i);
        reg1.leDoArq(arquivo);
        seekArq(j);
        reg2.leDoArq(arquivo);

        while (i < j)
        {
            comp++;
            seekArq(i);
            reg1.leDoArq(arquivo);
            while (reg1.getNumero() < pivo.getNumero())
            {
                i++;
                seekArq(i);
                reg1.leDoArq(arquivo);
                comp++;
            }
            comp++;
            seekArq(j);
            reg2.leDoArq(arquivo);
            while (reg2.getNumero() > pivo.getNumero())
            {
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
                comp++;
            }

            if (i <= j)
            {
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
                mov += 2;
                i++;
                j--;
            }
        }
        if (ini < j)
        {
            QuickCPArq(ini, j);
        }
        if (i < fim)
        {
            QuickCPArq(i, fim);
        }
    }

    public void QuickSemPivoArq() throws IOException
    {
        int tl = (int) filesize();
        QuickSPArq(0, tl - 1);
    }

    public void QuickSPArq(int ini, int fim)
    {
        int i = ini, j = fim;
        Registro reg1 = new Registro(), reg2 = new Registro();

        seekArq(i);
        reg1.leDoArq(arquivo);
        seekArq(j);
        reg2.leDoArq(arquivo);
        while (i < j)
        {
            comp++;
            seekArq(i);
            reg1.leDoArq(arquivo);
            while (i != j && reg1.getNumero() <= reg2.getNumero())
            {
                i++;
                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(j);
                reg2.leDoArq(arquivo);
                comp++;
            }
            seekArq(i);
            reg2.gravaNoArq(arquivo);
            seekArq(j);
            reg1.gravaNoArq(arquivo);
            comp++;
            seekArq(i);
            reg1.leDoArq(arquivo);
            while (i != j && reg2.getNumero() >= reg1.getNumero())
            {
                j--;
                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(j);
                reg2.leDoArq(arquivo);
                comp++;
            }
            seekArq(i);
            reg2.gravaNoArq(arquivo);
            seekArq(j);
            reg1.gravaNoArq(arquivo);
            mov += 2;
        }
        if (ini < i - 1)
        {
            QuickSPArq(ini, i - 1);
        }
        if (j + 1 < fim)
        {
            QuickSPArq(j + 1, fim);
        }
    }

    public void particaoMerge(Arquivo arq1, Arquivo arq2, int tl)
    {
        Registro reg1 = new Registro();
        for (int i = 0, j = tl / 2; i < tl / 2; i++, j++)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            reg1.gravaNoArq(arq1.getFile());

            seekArq(j);
            reg1.leDoArq(arquivo);
            reg1.gravaNoArq(arq2.getFile());
        }
    }

    public void fusaoMerge(Arquivo arq1, Arquivo arq2, int seq, int tl)
    {
        int i = 0, j = 0, k = 0, auxSeq = seq;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        while (k < tl)
        {
            while (i < seq && j < seq)
            {
                arq1.seekArq(i);
                reg1.leDoArq(arq1.getFile());
                arq2.seekArq(j);
                reg2.leDoArq(arq2.getFile());
                comp++;
                if (reg1.getNumero() < reg2.getNumero())
                {
                    seekArq(k);
                    reg1.gravaNoArq(arquivo);
                    i++;
                    mov++;
                } else
                {
                    seekArq(k);
                    reg2.gravaNoArq(arquivo);
                    j++;
                    mov++;
                }
                k++;
            }
            while (i < seq)
            {
                seekArq(k);
                reg1.gravaNoArq(arquivo);
                k++;
                i++;
                mov++;
            }
            while (j < seq)
            {
                seekArq(k);
                reg2.gravaNoArq(arquivo);
                k++;
                j++;
                mov++;
            }
            seq += auxSeq;
        }
    }

    public void MergeSortArq() throws IOException
    {
        int seq = 1;
        Arquivo arq1 = new Arquivo("arq1.dat");
        Arquivo arq2 = new Arquivo("arq2.dat");
        int tl = (int) filesize();

        while (seq <= tl / 2)
        {
            particaoMerge(arq1, arq2, tl);
            fusaoMerge(arq1, arq2, seq, tl);
            seq *= 2;
        }

    }

    public void mergeSortArqII() throws IOException
    {
        Arquivo arq1 = new Arquivo("merge2.dat");
        int tl = (int) filesize();
        mergeArqRecursivo(arq1, 0, tl - 1);
    }

    public void mergeArqRecursivo(Arquivo arq1, int esq, int dir)
    {
        int meio;
        if (esq < dir)
        {
            meio = (esq + dir) / 2;
            mergeArqRecursivo(arq1, esq, meio);
            mergeArqRecursivo(arq1, meio + 1, dir);
            fusaoMergeArq(arq1, esq, meio, meio + 1, dir);
        }
    }

    public void fusaoMergeArq(Arquivo arq, int ini1, int fim1, int ini2, int fim2)
    {
        int i = ini1, j = ini2, k = 0;
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        while (i <= fim1 && j <= fim2)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            comp++;
            if (reg1.getNumero() < reg2.getNumero())
            {
                arq.seekArq(k);
                reg1.gravaNoArq(arq.getFile());
                i++;
                mov++;
            } else
            {
                arq.seekArq(k);
                reg2.gravaNoArq(arq.getFile());
                j++;
                mov++;
            }
            k++;
        }
        while (i <= fim1)
        {
            arq.seekArq(k);
            reg1.gravaNoArq(arq.getFile());
            i++;
            k++;
            mov++;
        }
        while (j <= fim2)
        {
            arq.seekArq(k);
            reg2.gravaNoArq(arq.getFile());
            j++;
            k++;
            mov++;
        }

        for (i = 0; i < k; i++)
        {
            arq.seekArq(i);
            reg1.leDoArq(arq.getFile());
            seekArq(i + ini1);
            reg1.gravaNoArq(arquivo);
            mov += 2;
        }
    }

    public void CountingSort() throws IOException
    {

        Registro reg1 = new Registro();
        int maior = getMax();
        int[] aux = new int[10];
        int tl = (int) filesize();

        seekArq(0);
        for (int i = 0; i < tl; i++)
        {
            reg1.leDoArq(arquivo);
            aux[reg1.getNumero()]++;
        }
        for (int i = 0, j = 0; j <= maior; j++)
        {
            for (int k = aux[j]; k > 0; k--)
            {
                seekArq(i);
                reg1.setNumero(j);
                reg1.gravaNoArq(arquivo);
                i++;
            }
        }

    }

    public int getQuantidade()
    {
        Registro reg1 = new Registro();
        int i = 0;
        seekArq(i);
        while (!eof())
        {
            reg1.leDoArq(arquivo);
            i++;
        }
        return i;
    }

    public void BucketSort() throws IOException
    {
        int n = getQuantidade();
        int tl = (int) filesize();
        Registro reg1 = new Registro();
        Lista[] baldes = new Lista[n];
        No aux;
        for (int i = 0; i < n; i++) // INSTANCIANDO OS BALDES
        {
            baldes[i] = new Lista();
        }
        seekArq(0);
        for (int i = 0; i < tl; i++) // INSERE OS ELEMENTOS NO BALDE
        {
            reg1.leDoArq(arquivo);
            baldes[reg1.getNumero() / n].insert(reg1.getNumero());
        }
        for (int i = 0; i < baldes.length; i++)
        {
            Heap(baldes[i]);
        }
        for (int i = 0, k = 0; i < baldes.length; i++)
        {
            aux = baldes[i].getInicio();
            while (aux != null)
            {
                reg1.setNumero(aux.getInfo());
                seekArq(k++);
                reg1.gravaNoArq(arquivo);
                aux = aux.getProx();
                mov++;
            }
        }
    }

    public void Heap(Lista l) // BUCKET
    {
        int tl2 = l.getTl();
        int pai, fe, fd;
        No nfe, nfd, npai, nMaiorF;
        while (tl2 > 1)
        {
            pai = tl2 / 2 - 1;
            while (pai >= 0)
            {
                npai = l.getIndex(pai);
                fe = 2 * pai + 1;
                fd = fe + 1;
                if (fd < tl2)
                {
                    nfd = l.getIndex(fd);
                    nfe = l.getIndex(fe);

                    nMaiorF = (nfe.getInfo() > nfd.getInfo()) ? nfe : nfd;
                } else
                {
                    nMaiorF = l.getIndex(fe);
                }
                comp++;
                if (nMaiorF.getInfo() > npai.getInfo())
                {
                    int aux = nMaiorF.getInfo();
                    nMaiorF.setInfo(npai.getInfo());
                    npai.setInfo(aux);
                }
                pai--;
            }
            int aux = l.getIndex(0).getInfo();
            l.getIndex(0).setInfo(l.getIndex(tl2 - 1).getInfo());
            l.getIndex(tl2 - 1).setInfo(aux);
            tl2--;
        }
    }
    
    public void TimSort() throws IOException
    {
        int RUN = 32, tl = (int) filesize();
        for (int i = 0; i < tl; i += RUN)
        {
            if(i + (RUN-1) > tl - 1)
            {
                insertionSortTim(i, tl);
            }
            else
                insertionSortTim(i, RUN);
        }
        for (int size = RUN; size < tl; size *= 2)
        {
            for (int esq = 0; esq < tl; esq += 2*size)
            {
                int meio = esq + size + 1;
                int dir = esq+2*size - 1 > tl-1? tl-1 : esq+2*size - 1;
                
                mergeSortTim(esq, meio, dir);
            }
            
        }
    }
    
    public void insertionSortTim(int left, int right) // TIM SORT
    {
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();
        
        for (int i = left+1; i < right; i++)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(i - 1);
            reg2.leDoArq(arquivo);
            comp++;
            for (int j = i - 1; j >= 0 && reg2.getNumero() > reg1.getNumero(); j--)
            {
                seekArq(j);
                reg1.gravaNoArq(arquivo);
                reg2.gravaNoArq(arquivo);
                seekArq(j - 1);
                reg2.leDoArq(arquivo);
                reg1.leDoArq(arquivo);
                comp++;
                mov+=2;
            }
        }
    }
    
    public void mergeSortTim(int esq, int meio, int dir)
    {
        Arquivo aux = new Arquivo("timsort.dat");
        if(esq < dir)
        {
            mergeArqRecursivo(aux, esq, meio);
            mergeArqRecursivo(aux, meio+1, dir);
            fusaoMergeArq(aux, esq, meio, meio+1, dir);
        }
    }
} // FIM DA CLASSE

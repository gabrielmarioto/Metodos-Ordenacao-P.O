/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.de.ordenacao.p.o;

/**
 *
 * @author Gabriel
 */
public class Lista
{

    private int tl;
    private No inicio, fim;
    private int comp, mov;

    public Lista()
    {
        this.tl = 0;
        this.inicio = null;
        this.fim = null;
    }

    public int getTl()
    {
        return tl;
    }

    public void setTl(int tl)
    {
        this.tl = tl;
    }

    public No getInicio()
    {
        return inicio;
    }

    public void setInicio(No inicio)
    {
        this.inicio = inicio;
    }

    public int getComp()
    {
        return comp;
    }

    public void setComp(int comp)
    {
        this.comp = comp;
    }

    public void setMov(int mov)
    {
        this.mov = mov;
    }

    public int getMov()
    {
        return mov;
    }

    public No getFim()
    {
        return fim;
    }

    public void setFim(No fim)
    {
        this.fim = fim;
    }

    public No getIndex(int num)
    {
        No aux = inicio;

        for (int i = 0; i < num && aux != null; i++)
        {
            aux = aux.getProx();
        }

        return aux;
    }

    public void insert(int info)
    {
        No aux = inicio;
        No novo = new No(info);

        if (inicio == null)
        {
            tl++;
            inicio = novo;
        } else
        {
            while (aux.getProx() != null)
            {
                aux = aux.getProx();
            }

            novo.setAnt(aux);
            aux.setProx(novo);
            tl++;
        }
        fim = novo;
    }

    public void exibe()
    {
        No aux = inicio;

        while (aux != null)
        {
            System.out.println("Info: " + aux.getInfo());
            aux = aux.getProx();
        }
    }

    public No getPosNode(No inicio, int pos)
    {
        No aux = inicio;
        int i = 0;
        if (i < tl - 1 && aux != null)
        {
            while (i <= pos)
            {
                aux = aux.getProx();
                i++;
            }
        }
        return aux;
    }

    public void InsercaoDiretaLista()
    {
        No i = inicio.getProx();
        int aux;
        No pos = null;
        while (i != null)
        {
            pos = i;
            aux = pos.getInfo();
            while (pos != inicio && aux < pos.getAnt().getInfo())
            {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            i = i.getProx();
        }
    }

    public void selecaoDiretaLista()
    {
        No aux, j, posmenor;

        int i, menor;

        aux = inicio;
        while (aux.getProx() != null)
        {
            menor = aux.getInfo();
            i = aux.getInfo();
            j = aux.getProx();
            posmenor = aux;
            while (j != null)
            {
                if (j.getInfo() < menor)
                {
                    menor = j.getInfo();
                    posmenor = j;
                }
                j = j.getProx();
            }
            aux.setInfo(menor);
            posmenor.setInfo(i);
            aux = aux.getProx();
        }
    }

    public void BubbleSortLista()
    {
        int tl = getTl();
        No aux = inicio;
        No fim = this.fim;
        int temp;
        while (aux != fim)
        {
            while (aux.getProx() != null)
            {
                if (aux.getInfo() > aux.getProx().getInfo())
                {
                    temp = aux.getInfo();
                    aux.setInfo(aux.getProx().getInfo());
                    aux.getProx().setInfo(temp);
                }
                aux = aux.getProx();
            }
            aux = inicio;
            fim = fim.getAnt();
        }
    }

    public void ShakeSortLista()
    {
        No aux = inicio;
        No vai = inicio, vem = fim;
        No ult = fim;
        int temp;

        while (aux != ult)
        {
            while (aux.getProx() != null)
            {
                if (aux.getInfo() > aux.getProx().getInfo())
                {
                    temp = aux.getInfo();
                    aux.setInfo(aux.getProx().getInfo());
                    aux.getProx().setInfo(temp);
                }
                aux = aux.getProx();
            }
            while (ult.getAnt() != null)
            {
                if (ult.getInfo() < ult.getAnt().getInfo())
                {
                    temp = ult.getInfo();
                    ult.setInfo(ult.getAnt().getInfo());
                    ult.getAnt().setInfo(temp);
                }
                ult = ult.getAnt();
            }

            vai = vai.getProx();
            aux = vai;
            vem = vem.getAnt();
            ult = vem;
        }
    }

    public void QuickSemPivo()
    {
        QuickSP(inicio, fim);
    }

    public void QuickSP(No ini, No fim)
    {
        No i = ini, j = fim;
        int aux;

        while (i != j)
        {
            while (i != j && i.getInfo() <= j.getInfo())
            {
                i = i.getProx();
            }
            while (i != j && j.getInfo() >= i.getInfo())
            {
                j = j.getAnt();
            }
            if (i != j)
            {
                aux = j.getInfo();
                j.setInfo(i.getInfo());
                i.setInfo(aux);
                mov+=2;
            }
        }
        if (ini != i)
        {
            QuickSP(ini, i.getAnt());
        }
        if (j != fim)
        {
            QuickSP(j.getProx(), fim);
        }
    }
    
    public int buscaBinaria(int chave, int tl)
    {
        int inicio = 0, fim = tl-1, meio = tl/2;
        
        No aux = getIndex(meio);
        while(inicio < fim && aux.getInfo() != chave)
        {
            if(aux.getInfo() == chave)
                return meio;
            else if(chave > aux.getInfo())
                inicio = meio+1;
            else 
                fim = meio - 1;
            
            meio = (inicio + fim) / 2;
            aux = getIndex(meio);
        }
        if(chave > aux.getInfo())
            return meio + 1;
        return meio;
    }
    public void insercaoBinariaLista()
    {
        int pos, j = 0;
        No aux = inicio.getProx(), reg1;
        int i = 1;
        while(aux != null)
        {
            pos = buscaBinaria(aux.getInfo(), i); // NUMERO E TAMANHO DO TL
            reg1 = aux;
            j = i;
            while(j > pos)
            {
                reg1.setInfo(reg1.getAnt().getInfo());
                reg1 = reg1.getAnt();
                j--;
            }
            reg1.setInfo(aux.getInfo());
            i++;
            aux = aux.getProx();
        }
    }
} // FIM DA CLASSE LISTA

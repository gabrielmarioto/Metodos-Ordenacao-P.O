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

    public No getFim()
    {
        return fim;
    }

    public void setFim(No fim)
    {
        this.fim = fim;
    }
    
    
}

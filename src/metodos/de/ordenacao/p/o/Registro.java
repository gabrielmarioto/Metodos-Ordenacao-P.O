/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos.de.ordenacao.p.o;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Gabriel
 */
public class Registro
{
    public final int tf = 1022;
    private int numero; //4 bytes
    private char lixo[] = new char[tf]; //2044 bytes

    public Registro()
    {
        this.numero = 0;
        for (int i = 0; i < tf; i++)
        {
            lixo[i] = '?';
        }
    }

    public Registro(int numero)
    {
        this.numero = numero;
        for (int i = 0; i < tf; i++)
        {
            lixo[i] = '?';
        }
    }
    
    public void gravaNoArq(RandomAccessFile arquivo)
    {
        try
        {
            arquivo.writeInt(numero);
            for (int i = 0; i < tf; i++)
            {
                arquivo.writeChar(lixo[i]);
            }
        } catch (IOException e)
        {
        }
    }

    public void leDoArq(RandomAccessFile arquivo)
    {
        try
        {
            numero = arquivo.readInt();
            for (int i = 0; i < tf; i++)
            {
                lixo[i] = arquivo.readChar();
            }
        } catch (IOException e)
        {
        }
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    static int length()
    {
        //int numero; 4 bytes
        //char lixo[] = new char[tf]; 2044 bytes
        //--------------------------------------
        // 2048 bytes
        return (2048);
    }

}

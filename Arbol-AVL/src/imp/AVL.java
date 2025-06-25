package imp;

import api.ABBTDA;

public class AVL implements ABBTDA {

    class NodoAVL {
        int info;
        int altura;
        ABBTDA hijoIzq;
        ABBTDA hijoDer;
    }

    NodoAVL raiz;

    public void inicializarArbol() {
        raiz = null;
    }

    public void agregarElem(int x) {
        raiz = agregarAVL(raiz, x);
    }

    private NodoAVL agregarAVL(NodoAVL nodo, int x) {
        if (nodo == null) {
            NodoAVL nuevo = new NodoAVL();
            nuevo.info = x;
            nuevo.altura = 1;
            nuevo.hijoIzq = new AVL();
            nuevo.hijoIzq.inicializarArbol();
            nuevo.hijoDer = new AVL();
            nuevo.hijoDer.inicializarArbol();
            return nuevo;
        }

        if (x < nodo.info) {
            obtenerHijo(nodo.hijoIzq).agregarElem(x);
        } else if (x > nodo.info) {
            obtenerHijo(nodo.hijoDer).agregarElem(x);
        } else {
            return nodo;
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    public void eliminarElem(int x) {
        raiz = eliminarAVL(raiz, x);
    }

    private NodoAVL eliminarAVL(NodoAVL nodo, int x) {
        if (nodo == null) return null;

        if (x < nodo.info) {
            obtenerHijo(nodo.hijoIzq).eliminarElem(x);
        } else if (x > nodo.info) {
            obtenerHijo(nodo.hijoDer).eliminarElem(x);
        } else {
            if (nodo.hijoIzq.arbolVacio() && nodo.hijoDer.arbolVacio()) {
                return null;
            } else if (!nodo.hijoIzq.arbolVacio()) {
                nodo.info = mayor(nodo.hijoIzq);
                obtenerHijo(nodo.hijoIzq).eliminarElem(nodo.info);
            } else {
                nodo.info = menor(nodo.hijoDer);
                obtenerHijo(nodo.hijoDer).eliminarElem(nodo.info);
            }
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private void actualizarAltura(NodoAVL nodo) {
        int alturaIzq = obtenerAltura(nodo.hijoIzq);
        int alturaDer = obtenerAltura(nodo.hijoDer);
        nodo.altura = 1 + (alturaIzq > alturaDer ? alturaIzq : alturaDer);
    }

    private int calcularBalance(NodoAVL nodo) {
        return obtenerAltura(nodo.hijoIzq) - obtenerAltura(nodo.hijoDer);
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int balance = calcularBalance(nodo);

        if (balance > 1) {
            if (calcularBalance(obtenerHijo(nodo.hijoIzq).raiz) < 0) {
                AVL aux = obtenerHijo(nodo.hijoIzq);
				aux.raiz = aux.rotarIzquierda(aux.raiz);
				nodo.hijoIzq = aux;
			}
            return rotarDerecha(nodo);
        } 
        if (balance < -1) {
            if (calcularBalance(obtenerHijo(nodo.hijoDer).raiz) > 0) {
                AVL aux = obtenerHijo(nodo.hijoDer);
				aux.raiz = aux.rotarDerecha(aux.raiz);
				nodo.hijoDer = aux;
			}
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = obtenerHijo(y.hijoIzq).raiz;
        ABBTDA T2 = x.hijoDer;

        x.hijoDer = new AVL();
        ((AVL) x.hijoDer).raiz = y;
        y.hijoIzq = T2;

        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = obtenerHijo(x.hijoDer).raiz;
        ABBTDA T2 = y.hijoIzq;

        y.hijoIzq = new AVL();
        ((AVL) y.hijoIzq).raiz = x;
        x.hijoDer = T2;

        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private int obtenerAltura(ABBTDA hijo) {
        if (hijo == null || hijo.arbolVacio()) {
            return 0;
        }
        return obtenerHijo(hijo).raiz.altura;
    }

    private int mayor(ABBTDA a) {
        if (a.hijoDer().arbolVacio()) return a.raiz();
        return mayor(a.hijoDer());
    }

    private int menor(ABBTDA a) {
        if (a.hijoIzq().arbolVacio()) return a.raiz();
        return menor(a.hijoIzq());
    }

    public int raiz() {
        return raiz.info;
    }

    public ABBTDA hijoIzq() {
        return raiz.hijoIzq;
    }

    public ABBTDA hijoDer() {
        return raiz.hijoDer;
    }

    public boolean arbolVacio() {
        return raiz == null;
    }

    private AVL obtenerHijo(ABBTDA arbol) {
        return (AVL) arbol;
    }
}

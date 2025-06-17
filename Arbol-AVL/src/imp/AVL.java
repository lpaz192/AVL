package imp;

import api.ABBTDA;

public class AVL implements ABBTDA {

	class NodoAVL {
		int info;
		int altura;
		AVL hijoIzq;
		AVL hijoDer;
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
			nodo.hijoIzq.agregarElem(x);
		} else if (x > nodo.info) {
			nodo.hijoDer.agregarElem(x);
		} else {
			return nodo; // elemento duplicado, no se inserta
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
			nodo.hijoIzq.eliminarElem(x);
		} else if (x > nodo.info) {
			nodo.hijoDer.eliminarElem(x);
		} else {
			if (nodo.hijoIzq.arbolVacio() && nodo.hijoDer.arbolVacio()) {
				return null;
			} else if (!nodo.hijoIzq.arbolVacio()) {
				nodo.info = mayor(nodo.hijoIzq);
				nodo.hijoIzq.eliminarElem(nodo.info);
			} else {
				nodo.info = menor(nodo.hijoDer);
				nodo.hijoDer.eliminarElem(nodo.info);
			}
		}

		actualizarAltura(nodo);
		return balancear(nodo);
	}

	private void actualizarAltura(NodoAVL nodo) {
		int alturaIzq = (nodo.hijoIzq == null || nodo.hijoIzq.arbolVacio()) ? 0 : nodo.hijoIzq.raiz.altura;
		int alturaDer = (nodo.hijoDer == null || nodo.hijoDer.arbolVacio()) ? 0 : nodo.hijoDer.raiz.altura;
		nodo.altura = 1 + (alturaIzq > alturaDer ? alturaIzq : alturaDer);
	}

	private int calcularBalance(NodoAVL nodo) {
		int alturaIzq = (nodo.hijoIzq == null || nodo.hijoIzq.arbolVacio()) ? 0 : nodo.hijoIzq.raiz.altura;
		int alturaDer = (nodo.hijoDer == null || nodo.hijoDer.arbolVacio()) ? 0 : nodo.hijoDer.raiz.altura;
		return alturaIzq - alturaDer;
	}

	private NodoAVL balancear(NodoAVL nodo) {
		int balance = calcularBalance(nodo);

		if (balance > 1) {
			if (calcularBalance(nodo.hijoIzq.raiz) < 0) {
				nodo.hijoIzq.raiz = rotarIzquierda(nodo.hijoIzq.raiz);
			}
			return rotarDerecha(nodo);
		} 
		if (balance < -1) {
			if (calcularBalance(nodo.hijoDer.raiz) > 0) {
				nodo.hijoDer.raiz = rotarDerecha(nodo.hijoDer.raiz);
			}
			return rotarIzquierda(nodo);
		}
		return nodo;
	}

	private NodoAVL rotarDerecha(NodoAVL y) {
		NodoAVL x = y.hijoIzq.raiz;
		AVL T2 = x.hijoDer;

		x.hijoDer = new AVL();
		x.hijoDer.raiz = y;
		y.hijoIzq = T2;

		actualizarAltura(y);
		actualizarAltura(x);
		return x;
	}

	private NodoAVL rotarIzquierda(NodoAVL x) {
		NodoAVL y = x.hijoDer.raiz;
		AVL T2 = y.hijoIzq;

		y.hijoIzq = new AVL();
		y.hijoIzq.raiz = x;
		x.hijoDer = T2;

		actualizarAltura(x);
		actualizarAltura(y);
		return y;
	}

	private int mayor(AVL a) {
		if (a.raiz.hijoDer == null || a.raiz.hijoDer.arbolVacio()) return a.raiz.info;
		return mayor(a.raiz.hijoDer);
	}

	private int menor(AVL a) {
		if (a.raiz.hijoIzq == null || a.raiz.hijoIzq.arbolVacio()) return a.raiz.info;
		return menor(a.raiz.hijoIzq);
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
}

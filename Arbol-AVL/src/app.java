import api.ABBTDA;
import imp.AVL;

public class app {

    public static void preOrder(ABBTDA a) {
        if (!a.arbolVacio()) {
            System.out.print(a.raiz() + " ");
            preOrder(a.hijoIzq());
            preOrder(a.hijoDer());
        }
    }

    public static void inOrder(ABBTDA a) {
        if (!a.arbolVacio()) {
            inOrder(a.hijoIzq());
            System.out.print(a.raiz() + " ");
            inOrder(a.hijoDer());
        }
    }

    public static void postOrder(ABBTDA a) {
        if (!a.arbolVacio()) {
            postOrder(a.hijoIzq());
            postOrder(a.hijoDer());
            System.out.print(a.raiz() + " ");
        }
    }

    public static void main(String[] args) {
        ABBTDA arbol = new AVL();
        arbol.inicializarArbol();

        arbol.agregarElem(17);
        arbol.agregarElem(8);
        arbol.agregarElem(48);
        arbol.agregarElem(27);
        arbol.agregarElem(55);
        arbol.agregarElem(22);
        arbol.agregarElem(39);
        arbol.agregarElem(35);
        arbol.agregarElem(40);

        System.out.print("PreOrder: ");
        preOrder(arbol);
        System.out.println();

        System.out.print("InOrder: ");
        inOrder(arbol);
        System.out.println();

        System.out.print("PostOrder: ");
        postOrder(arbol);
        System.out.println();
    }
}

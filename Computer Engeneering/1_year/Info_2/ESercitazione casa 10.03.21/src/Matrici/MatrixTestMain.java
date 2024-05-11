package Matrici;
/**
 * Classe di test
 * 
 * @author Fondamenti di Informatica T-2 marzo 2021
 */
public class MatrixTestMain {
	public static void main(String[] args) {
		Matrix m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		System.out.println(m);
		System.out.println("il det di m e' "+m.det());

		Matrix m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 },
				{ 0, 0, 1 } });
		System.out.println(m1);
		System.out.println("il det di m1 e' "+m1.det());

		Matrix sum_m_m1 = m.sum(m1);
		System.out.println("m + m1 =");
		System.out.println(sum_m_m1);

		Matrix mul_m_m1 = m.mul(m1);
		System.out.println("m * m1 =");
		System.out.println(mul_m_m1);

		Matrix subMatrix = m.extractSubMatrix(1, 1, 2, 2);
		System.out.println("SubMatrix: ");
		System.out.println(subMatrix);

		Matrix minor = m1.extractMinor(2, 2);
		System.out.println("Minor: ");
		System.out.println(minor);
		
		Matrix m3 = new Matrix(new double[][] { {1, 2.2, 5, 8 }, {8, 7, 9, 9}, {21, 45, 0, 10}, {11, 12, 13, 14}});
		System.out.println(m3);
		System.out.println("il determinante della matrice m3 e' "+m3.det()+"\n\n");
		
		Matrix m4 = new Matrix(new double[][] { {1, 2.2, 5, 8, Math.PI }, {8, 7, 9, 9, 10}, {21, 45, 0, 10, Math.E}, {11, 12, 13, 14, Math.sqrt(2)}, 
			{17, 15, 21, 7/2, Math.sqrt(3)}});
		System.out.println(m4);
		System.out.println("il determinante della matrice m4 e' "+m4.det());
		
		Matrix m5 = new Matrix(new double[][] { { Math.PI, Math.E, 2 }, { 17, 19, 58 },
			{ 0, 0, 1 } });
		System.out.println("la matrice inversa di m5 e' \n"+m5.inverse());

	}

}

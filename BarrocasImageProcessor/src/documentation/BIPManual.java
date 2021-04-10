package documentation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.*;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
public class BIPManual {
	private static String dest = "C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\barrocasitexttest.pdf";
	PdfWriter writer;
	PdfDocument document;
	Vector <Object> elements = new Vector <Object> ();
	private String [] contents = new String [] {
			"BARROCAS' IMAGE PROCESSOR"
			,
			"Querido pai\r\n" +
			"Quero introduzir a voc� o nosso Software chamado "
			+ "Barrocas' Image Processor. Fi-lo porque quero que ele "
			+ "seja uma ferramenta que lhe ajude nas atividades que voc� "
			+ "quer desenvolver para o seu projeto na UFC. Espero que este "
			+ "Software lhe ajude a resolver os problemas que voc� enfrentar "
			+ "de agora em diante no seu projeto.\r\n" +
			"Abra�o\r\n" +
			"Samuel\r\n"
			,
			"INTRODU��O: SISTEMA DE CORES RGB\r\n"
			,
			"A sigla RGB vem de Red-Green-Blue (Vermelho-Verde-Azul). Este sistema "
			+ "� formado por uma tripla de valores em que cada um deles varia de 0 at� 255. "
			+ "Cada valor representa a intensidade de vermelho, verde e azul, e a combina��o "
			+ "destas intensidades forma a cor do pixel em quest�o. Por exemplo, uma tripla "
			+ "(230, 45, 56) indica uma cor cuja intensidade de vermelho � 230, de verde � 45 e "
			+ "de azul � 56. Como uma conseq��ncia desta defini��o, a tripla (0, 0, 0) representa "
			+ "a cor preta (que � a aus�ncia de qualquer intensidade de cor), e a tripla "
			+ "(255, 255, 255) representa a cor branca (que � a sobreposi��o de todas as cores at� "
			+ "o n�vel m�ximo). Outra tripla not�vel � a que representa o vermelho (255, 0, 0).\r\n"
			,
			"::path:: C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\Figures\\figure1.png"
			,
			"Neste estado, os �nicos bot�es habilitados s�o \"Load Image\" e \"Choose File\", e � poss�vel tamb�m alterar o caminho "
			+ "do arquivo que se deseja abrir, tanto editando a �rea de texto do caminho quanto em clicando em Choose File e "
			+ " abrindo o arquivo desejado. Ao abrirmos o arquivo Samuel012.jpg no caminho especificado, "
			+ "obtemos a seguinte tela:\r\n" + 
			""
			,
			"::path:: C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\Figures\\figure2.png"
			,
			"Desta tela, � poss�vel fazer algumas opera��es b�sicas: somar valores (bot�o \"Sum to Image\"), subtrair (bot�o \"Sub to Image\"),"
			+ "diminuir a resolu��o da imagem (\"Decrease Figure Size (Only Odd Pixels)\"), ou etc... Para que o usu�rio tenha"
			+ "acesso a op��es avan�adas, deve clicar no bot�o Custom. Ap�s o clique neste bot�o, a seguinte tela sera exibida:"
			,
			"::path:: C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\Figures\\figure3.png"
	};
	public Paragraph paragraph (String str) {
		Paragraph p = new Paragraph ();
		p.setTextAlignment(TextAlignment.JUSTIFIED);
		p.add (str);
		//p.setFontSize (18);
		//paragraphs.addElement(p);
		return p;
	}
	/*public Image imageParagraph (Image image) {
		Object p = image;
		//p.setTextAlignment(TextAlignment.JUSTIFIED);
		p.add (image);
		return p;
	}*/
	public void addElements (String [] strs) {
		int size = strs.length;
		for (int i = 0; i < size; i++) {
			if (!strs[i].contains("::path::")) {
				elements.addElement (paragraph (strs [i]));
			}
			else {
				StringTokenizer st = new StringTokenizer (strs [i]);
				Vector <String> vec = new Vector <String> ();
				while (st.hasMoreTokens()) {
					vec.addElement (st.nextToken());
				}
				try {
					java.awt.Image image = ImageIO.read(new File (vec.elementAt(1)));
					//image = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
					ImageData imageData = ImageDataFactory.create(image, null);
					elements.addElement (new Image (imageData));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
	public Document parsToDoc (Vector <Object> paras, PdfDocument pdfdoc) {
		int size = paras.size();
		Document d = new Document (pdfdoc);
		for (int i = 0; i < size; i++) {
			if (paras.elementAt(i) instanceof Paragraph) {
				d.add ((Paragraph)paras.elementAt(i));
			}
			else {
				d.add ((Image)paras.elementAt(i));
			}
		}
		return d;
	}
	public BIPManual (String path) {
		try {
			writer = new PdfWriter (path);
			PdfDocument pdfdoc = new PdfDocument (writer);
			pdfdoc.addNewPage();
			addElements (contents);
			Document doc = parsToDoc (elements, pdfdoc);
	        doc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main (String [] args) {
		BIPManual manual = new BIPManual (dest);
	}
}

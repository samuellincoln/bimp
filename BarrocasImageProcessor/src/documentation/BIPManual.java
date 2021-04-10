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
			"Quero introduzir a você o nosso Software chamado "
			+ "Barrocas' Image Processor. Fi-lo porque quero que ele "
			+ "seja uma ferramenta que lhe ajude nas atividades que você "
			+ "quer desenvolver para o seu projeto na UFC. Espero que este "
			+ "Software lhe ajude a resolver os problemas que você enfrentar "
			+ "de agora em diante no seu projeto.\r\n" +
			"Abraço\r\n" +
			"Samuel\r\n"
			,
			"INTRODUÇÃO: SISTEMA DE CORES RGB\r\n"
			,
			"A sigla RGB vem de Red-Green-Blue (Vermelho-Verde-Azul). Este sistema "
			+ "é formado por uma tripla de valores em que cada um deles varia de 0 até 255. "
			+ "Cada valor representa a intensidade de vermelho, verde e azul, e a combinação "
			+ "destas intensidades forma a cor do pixel em questão. Por exemplo, uma tripla "
			+ "(230, 45, 56) indica uma cor cuja intensidade de vermelho é 230, de verde é 45 e "
			+ "de azul é 56. Como uma conseqüência desta definição, a tripla (0, 0, 0) representa "
			+ "a cor preta (que é a ausência de qualquer intensidade de cor), e a tripla "
			+ "(255, 255, 255) representa a cor branca (que é a sobreposição de todas as cores até "
			+ "o nível máximo). Outra tripla notável é a que representa o vermelho (255, 0, 0).\r\n"
			,
			"::path:: C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\Figures\\figure1.png"
			,
			"Neste estado, os únicos botões habilitados são \"Load Image\" e \"Choose File\", e é possível também alterar o caminho "
			+ "do arquivo que se deseja abrir, tanto editando a área de texto do caminho quanto em clicando em Choose File e "
			+ " abrindo o arquivo desejado. Ao abrirmos o arquivo Samuel012.jpg no caminho especificado, "
			+ "obtemos a seguinte tela:\r\n" + 
			""
			,
			"::path:: C:\\Users\\Samuel\\eclipse-workspace\\BarrocasImageProcessor\\Figures\\figure2.png"
			,
			"Desta tela, é possível fazer algumas operações básicas: somar valores (botão \"Sum to Image\"), subtrair (botão \"Sub to Image\"),"
			+ "diminuir a resolução da imagem (\"Decrease Figure Size (Only Odd Pixels)\"), ou etc... Para que o usuário tenha"
			+ "acesso a opções avançadas, deve clicar no botão Custom. Após o clique neste botão, a seguinte tela sera exibida:"
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

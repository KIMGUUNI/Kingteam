package question;


   import java.awt.Color;
   import java.awt.Font;
   import java.awt.FontMetrics;
   import java.awt.Graphics;
   import java.awt.Graphics2D;
   import java.awt.image.BufferedImage;
    
   public class DateGet {
        
       public static final int ART_SIZE_SMALL = 10;
       public static final int ART_SIZE_MEDIUM = 8;
       public static final int ART_SIZE_LARGE = 10;
       public static final int ART_SIZE_HUGE = 12;
    
       private static final String DEFAULT_ART_SYMBOL = "*";
    
       public enum ASCIIArtFont {
           ART_FONT_DIALOG("Dialog"), ART_FONT_DIALOG_INPUT("DialogInput"), 
           ART_FONT_MONO("Monospaced"),ART_FONT_SERIF("Serif"), ART_FONT_SANS_SERIF("SansSerif");
    
           private String value;
    
           public String getValue() {
               return value;
           }
    
           private ASCIIArtFont(String value) {
               this.value = value;
           }
       }
    
       public void dateGet2() throws Exception {
           DateGet artGen = new DateGet();

            
           System.out.println();
           artGen.printTextArt("1일 데이트권을", ART_SIZE_SMALL, ASCIIArtFont.ART_FONT_DIALOG,"#");
           Thread.sleep(800);
           artGen.printTextArt("♥  따  내  라  ♥", ART_SIZE_SMALL, ASCIIArtFont.ART_FONT_DIALOG,"#");
           System.out.println();
            
       }
    
       private void printTextArt(String artText, int textHeight, ASCIIArtFont fontType, String artSymbol) throws Exception {
           String fontName = fontType.getValue();
           int imageWidth = findImageWidth(textHeight, artText, fontName);
    
           BufferedImage image = new BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_BGR);
           Graphics g = image.getGraphics();
           Font font = new Font(fontName, Font.BOLD, textHeight);
           g.setFont(font);
    
           Graphics2D graphics = (Graphics2D) g;
           graphics.drawString(artText, 0, getBaselinePosition(g, font));
    
           for (int y = 0; y < textHeight; y++) {
               StringBuilder sb = new StringBuilder();
               for (int x = 0; x < imageWidth; x++)
                   sb.append(image.getRGB(x, y) == Color.WHITE.getRGB() ? artSymbol : " ");
               if (sb.toString().trim().isEmpty())
                   continue;
               System.out.println(sb);
           }
       }
    

       void printTextArt(String artText, int textHeight) throws Exception {
           printTextArt(artText, textHeight, ASCIIArtFont.ART_FONT_DIALOG, DEFAULT_ART_SYMBOL);
       }
    
       private int findImageWidth(int textHeight, String artText, String fontName) {
           BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
           Graphics g = im.getGraphics();
           g.setFont(new Font(fontName, Font.BOLD, textHeight));
           return g.getFontMetrics().stringWidth(artText);
       }
    

       private int getBaselinePosition(Graphics g, Font font) {
           FontMetrics metrics = g.getFontMetrics(font);
           int y = metrics.getAscent() - metrics.getDescent();
           return y;
       }
}
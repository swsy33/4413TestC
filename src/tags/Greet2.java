package tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

/*****************************************************************
Designed to demonstrate jsp xtags: capturing the xml attributes
and the tag body in java in order to create a custom replacement.
@since Fall-2015, @author: h. roumani
*****************************************************************/  
public class Greet2 extends SimpleTagSupport
{
        private String party;

        public void doTag() throws JspException, IOException
        {
                JspWriter os = this.getJspContext().getOut();
                
                StringWriter sw = new StringWriter();
                getJspBody().invoke(sw);
                os.write("<i>Attribute = "+this.party+"</i><br/>");
                os.write("<i>Body = "+sw.toString()+"</i><br/>");
                
        }
        public void setParty(String party)
        {
                this.party = party;
        }
}
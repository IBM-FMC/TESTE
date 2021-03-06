package ch.belsoft.hrassistant.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ch.belsoft.hrassistant.attachment.controller.AttachmentController;
import ch.belsoft.hrassistant.attachment.model.Attachment;
import ch.belsoft.hrassistant.attachment.model.AttachmentHolder;

public class ImageServlet extends BaseServlet {
    
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        super.service(servletRequest, servletResponse);
        try {
            AttachmentHolder attachmentHolder = null;
            Attachment attachment = null;
            
            if(getRouteParam().size()==2){
                //route param 1 = attachmentId
                //route param 2 = attachment name, user_accounts.png
                if( "GET".equals(getRequestMethod())) {
                    //get the attachment from the
                    String attachmentId = getRouteParam().get(0);
                    attachmentHolder = AttachmentController.get().findAttachment(attachmentId);
                }
            }
            
            if(attachmentHolder != null){
                if(attachmentHolder.getAttachments().containsKey(getRouteParam().get(1))){
                    attachment = attachmentHolder.getAttachments().get(getRouteParam().get(1));
                    if(attachment != null){
                        getOut().write(AttachmentController.get().decodeBase64Attachment(attachment));
                    }
                }
                
            }else{
                getOut().println("the url is not correct (/image/1234444/image.png");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            getOut().println(e.toString());
            
        } finally {
            getOut().close();
            
            // It shouldn't be null if things are going well, but a check never hurt
            if(getFacesContext() != null) {
                //complete the response and release the handle on the FacesContext instance
                getFacesContext().responseComplete();
                getFacesContext().release();
            }
        }
    }
    
}

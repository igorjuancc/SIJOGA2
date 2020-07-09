package br.com.sijoga.util;

import br.com.sijoga.mb.LoginMb;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class VerificaLogin implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String pagina = ctx.getViewRoot().getViewId();                           //Nome da pagina atual
            Application app = ctx.getApplication();
            LoginMb usuario = app.evaluateExpressionGet(ctx, "#{loginMb}", LoginMb.class);
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            int opcCase = 0;

            if (usuario.getAdvogado().getId() != 0) {
                opcCase = 1;
            }

            switch (opcCase) {
                case 0:
                    if ((!pagina.equals("/index.xhtml")) && (!pagina.equals("/login.xhtml")) && (!pagina.equals("/CadastroAdvogado.xhtml")) && (!pagina.equals("/CadastroJuiz.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/index.jsf");
                    }
                    break;
                case 1:
                    if ((!pagina.equals("/Advogado/InicioAdvogado.xhtml")) && (!pagina.equals("/Advogado/CadastroCliente.xhtml")) && (!pagina.equals("/Advogado/CadastroProcesso.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    }
                    break;
            }
        } catch (Exception e) {           
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(VerificaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }                              
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}

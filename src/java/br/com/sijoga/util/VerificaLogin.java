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
import javax.inject.Inject;

public class VerificaLogin implements PhaseListener {
    @Inject
    private LoginMb usuario;
    
    @Override
    public void afterPhase(PhaseEvent event) {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String pagina = ctx.getViewRoot().getViewId();                           //Nome da pagina atual
            Application app = ctx.getApplication();
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            int opcCase = 0;

            if (usuario.getAdvogado().getId() != 0) {
                opcCase = 1;
            } else if (usuario.getJuiz().getId() != 0) {
                opcCase = 2;                
            } else if (usuario.getParte().getId() != 0) {
                opcCase = 3;                
            }

            switch (opcCase) {
                case 0:
                    if ((!pagina.equals("/index.xhtml")) && (!pagina.equals("/login.xhtml")) && (!pagina.equals("/CadastroAdvogado.xhtml")) && (!pagina.equals("/CadastroJuiz.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/index.jsf");
                    }
                    break;
                case 1:
                    if ((!pagina.equals("/Advogado/InicioAdvogado.xhtml")) && (!pagina.equals("/Advogado/CadastroCliente.xhtml")) && (!pagina.equals("/Advogado/CadastroProcesso.xhtml")) && (!pagina.equals("/Advogado/VisualizarProcesso.xhtml"))
                         && (!pagina.equals("/Advogado/VisualizarFase.xhtml")) && (!pagina.equals("/Advogado/NovaFase.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    }
                    break;
                case 2:
                    if ((!pagina.equals("/Juiz/InicioJuiz.xhtml")) && (!pagina.equals("/Juiz/VisualizarProcesso.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/InicioJuiz.jsf");                        
                    }
                    break;
                case 3:
                    if ((!pagina.equals("/Parte/InicioParte.xhtml")) && (!pagina.equals("/Parte/VisualizarProcesso.xhtml")) && (!pagina.equals("/ErroPage.xhtml"))) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Parte/InicioParte.jsf");                        
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

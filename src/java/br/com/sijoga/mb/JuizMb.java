package br.com.sijoga.mb;

import br.com.sijoga.bean.Endereco;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.facade.CidadeFacade;
import br.com.sijoga.facade.JuizFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ManagedBean
@Named(value = "juizMb")
@ViewScoped
public class JuizMb implements Serializable {

    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Juiz juiz;
    private List<Estado> estados;
    private Estado estadoSelect;

    @PostConstruct
    public void init() {
        try {
            this.juiz = new Juiz();
            this.juiz.setEndereco(new Endereco());
            this.estados = CidadeFacade.listaEstado();
            this.estadoSelect = this.estados.get(0);
            this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(JuizMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarJuiz() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!this.juiz.getSenha().equals(this.confirmaSenha)) {
                msg = SijogaUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                List<String> mensagens = JuizFacade.cadastrarJuiz(this.juiz);
                if (!mensagens.isEmpty()) {
                    for (String print : mensagens) {
                        msg = SijogaUtil.emiteMsg(print, 2);
                        ctx.addMessage(null, msg);
                    }
                } else {
                    this.cadastroConcluido = true;
                }
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(JuizMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void buscaCidadePorEstado() {
        try {
            this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));                        
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(JuizMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Boolean getCadastroConcluido() {
        return cadastroConcluido;
    }

    public void setCadastroConcluido(Boolean cadastroConcluido) {
        this.cadastroConcluido = cadastroConcluido;
    }

    public Juiz getJuiz() {
        return juiz;
    }

    public void setJuiz(Juiz juiz) {
        this.juiz = juiz;
    }
    
    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstadoSelect() {
        return estadoSelect;
    }

    public void setEstadoSelect(Estado estadoSelect) {
        this.estadoSelect = estadoSelect;
    }
}

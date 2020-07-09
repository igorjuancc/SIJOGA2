package br.com.sijoga.mb;

import br.com.sijoga.bean.Endereco;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.facade.CidadeFacade;
import br.com.sijoga.facade.ParteFacade;
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
@Named(value = "parteMb")
@ViewScoped
public class ParteMb implements Serializable {

    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Parte parte;
    private List<Estado> estados;
    private Estado estadoSelect;

    @PostConstruct
    public void init() {
        try {
            this.parte = new Parte();
            this.parte.setEndereco(new Endereco());
            this.estados = CidadeFacade.listaEstado();
            this.estadoSelect = this.estados.get(0);
            this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarParte() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!this.parte.getSenha().equals(this.confirmaSenha)) {
                msg = SijogaUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                List<String> mensagens = ParteFacade.cadastrarParte(this.parte);
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
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
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

    public Parte getParte() {
        return parte;
    }

    public void setParte(Parte parte) {
        this.parte = parte;
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

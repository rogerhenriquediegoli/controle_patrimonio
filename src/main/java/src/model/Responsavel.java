package src.model;

import src.enums.SexoEnum;
import java.time.LocalDateTime;
import src.enums.StatusResponsavelEnum;

public class Responsavel {

    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private String setor;
    private String cargo;
    private SexoEnum sexo;
    private LocalDateTime dataCadastro;
    private StatusResponsavelEnum status;

    public Responsavel(){};

    public Responsavel(
            Long id,
            String nomeCompleto,
            String cpf,
            String email,
            String telefone,
            String setor,
            String cargo,
            SexoEnum sexo,
            LocalDateTime dataCadastro,
            StatusResponsavelEnum status) {
                
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.setor = setor;
        this.cargo = cargo;
        this.sexo = sexo;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public StatusResponsavelEnum getStatus() {
        return status;
    }

    public void setStatus(StatusResponsavelEnum status) {
        this.status = status;
    }
}
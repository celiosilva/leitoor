package br.com.delogic.leitoor.entidade.mapped;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.delogic.jfunk.data.Identity;
import br.com.delogic.leitoor.entidade.Aluno;

@MappedSuperclass
public abstract class DominioAluno<E> extends Identity<E> {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Aluno aluno;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

}

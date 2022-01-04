/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Habitante;
import Logica.PeceraDulce;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Agustina
 */
public class PeceraDulceJpaController implements Serializable {

    public PeceraDulceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PeceraDulceJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PeceraDulce peceraDulce) {
        if (peceraDulce.getListaHabitantes() == null) {
            peceraDulce.setListaHabitantes(new ArrayList<Habitante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Habitante> attachedListaHabitantes = new ArrayList<Habitante>();
            for (Habitante listaHabitantesHabitanteToAttach : peceraDulce.getListaHabitantes()) {
                listaHabitantesHabitanteToAttach = em.getReference(listaHabitantesHabitanteToAttach.getClass(), listaHabitantesHabitanteToAttach.getIdHabitante());
                attachedListaHabitantes.add(listaHabitantesHabitanteToAttach);
            }
            peceraDulce.setListaHabitantes(attachedListaHabitantes);
            em.persist(peceraDulce);
            for (Habitante listaHabitantesHabitante : peceraDulce.getListaHabitantes()) {
                Logica.Pecera oldPeceraOfListaHabitantesHabitante = listaHabitantesHabitante.getPecera();
                listaHabitantesHabitante.setPecera(peceraDulce);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
                if (oldPeceraOfListaHabitantesHabitante != null) {
                    oldPeceraOfListaHabitantesHabitante.getListaHabitantes().remove(listaHabitantesHabitante);
                    oldPeceraOfListaHabitantesHabitante = em.merge(oldPeceraOfListaHabitantesHabitante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PeceraDulce peceraDulce) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeceraDulce persistentPeceraDulce = em.find(PeceraDulce.class, peceraDulce.getIdPecera());
            List<Habitante> listaHabitantesOld = persistentPeceraDulce.getListaHabitantes();
            List<Habitante> listaHabitantesNew = peceraDulce.getListaHabitantes();
            List<Habitante> attachedListaHabitantesNew = new ArrayList<Habitante>();
            for (Habitante listaHabitantesNewHabitanteToAttach : listaHabitantesNew) {
                listaHabitantesNewHabitanteToAttach = em.getReference(listaHabitantesNewHabitanteToAttach.getClass(), listaHabitantesNewHabitanteToAttach.getIdHabitante());
                attachedListaHabitantesNew.add(listaHabitantesNewHabitanteToAttach);
            }
            listaHabitantesNew = attachedListaHabitantesNew;
            peceraDulce.setListaHabitantes(listaHabitantesNew);
            peceraDulce = em.merge(peceraDulce);
            for (Habitante listaHabitantesOldHabitante : listaHabitantesOld) {
                if (!listaHabitantesNew.contains(listaHabitantesOldHabitante)) {
                    listaHabitantesOldHabitante.setPecera(null);
                    listaHabitantesOldHabitante = em.merge(listaHabitantesOldHabitante);
                }
            }
            for (Habitante listaHabitantesNewHabitante : listaHabitantesNew) {
                if (!listaHabitantesOld.contains(listaHabitantesNewHabitante)) {
                    PeceraDulce oldPeceraOfListaHabitantesNewHabitante = (PeceraDulce) listaHabitantesNewHabitante.getPecera();
                    listaHabitantesNewHabitante.setPecera(peceraDulce);
                    listaHabitantesNewHabitante = em.merge(listaHabitantesNewHabitante);
                    if (oldPeceraOfListaHabitantesNewHabitante != null && !oldPeceraOfListaHabitantesNewHabitante.equals(peceraDulce)) {
                        oldPeceraOfListaHabitantesNewHabitante.getListaHabitantes().remove(listaHabitantesNewHabitante);
                        oldPeceraOfListaHabitantesNewHabitante = em.merge(oldPeceraOfListaHabitantesNewHabitante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = peceraDulce.getIdPecera();
                if (findPeceraDulce(id) == null) {
                    throw new NonexistentEntityException("The peceraDulce with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeceraDulce peceraDulce;
            try {
                peceraDulce = em.getReference(PeceraDulce.class, id);
                peceraDulce.getIdPecera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peceraDulce with id " + id + " no longer exists.", enfe);
            }
            List<Habitante> listaHabitantes = peceraDulce.getListaHabitantes();
            for (Habitante listaHabitantesHabitante : listaHabitantes) {
                listaHabitantesHabitante.setPecera(null);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
            }
            em.remove(peceraDulce);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PeceraDulce> findPeceraDulceEntities() {
        return findPeceraDulceEntities(true, -1, -1);
    }

    public List<PeceraDulce> findPeceraDulceEntities(int maxResults, int firstResult) {
        return findPeceraDulceEntities(false, maxResults, firstResult);
    }

    private List<PeceraDulce> findPeceraDulceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PeceraDulce.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PeceraDulce findPeceraDulce(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PeceraDulce.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeceraDulceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PeceraDulce> rt = cq.from(PeceraDulce.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

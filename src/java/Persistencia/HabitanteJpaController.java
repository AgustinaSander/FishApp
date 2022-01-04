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
import Logica.FichaMedica;
import Logica.Habitante;
import Logica.Pecera;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Agustina
 */
public class HabitanteJpaController implements Serializable {

    public HabitanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public HabitanteJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Habitante habitante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FichaMedica ficha = habitante.getFicha();
            if (ficha != null) {
                ficha = em.getReference(ficha.getClass(), ficha.getIdFicha());
                habitante.setFicha(ficha);
            }
            Pecera pecera = habitante.getPecera();
            if (pecera != null) {
                pecera = em.getReference(pecera.getClass(), pecera.getIdPecera());
                habitante.setPecera(pecera);
            }
            em.persist(habitante);
            if (ficha != null) {
                Habitante oldHabitanteOfFicha = ficha.getHabitante();
                if (oldHabitanteOfFicha != null) {
                    oldHabitanteOfFicha.setFicha(null);
                    oldHabitanteOfFicha = em.merge(oldHabitanteOfFicha);
                }
                ficha.setHabitante(habitante);
                ficha = em.merge(ficha);
            }
            if (pecera != null) {
                pecera.getListaHabitantes().add(habitante);
                pecera = em.merge(pecera);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Habitante habitante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitante persistentHabitante = em.find(Habitante.class, habitante.getIdHabitante());
            FichaMedica fichaOld = persistentHabitante.getFicha();
            FichaMedica fichaNew = habitante.getFicha();
            Pecera peceraOld = persistentHabitante.getPecera();
            Pecera peceraNew = habitante.getPecera();
            if (fichaNew != null) {
                fichaNew = em.getReference(fichaNew.getClass(), fichaNew.getIdFicha());
                habitante.setFicha(fichaNew);
            }
            if (peceraNew != null) {
                peceraNew = em.getReference(peceraNew.getClass(), peceraNew.getIdPecera());
                habitante.setPecera(peceraNew);
            }
            habitante = em.merge(habitante);
            if (fichaOld != null && !fichaOld.equals(fichaNew)) {
                fichaOld.setHabitante(null);
                fichaOld = em.merge(fichaOld);
            }
            if (fichaNew != null && !fichaNew.equals(fichaOld)) {
                Habitante oldHabitanteOfFicha = fichaNew.getHabitante();
                if (oldHabitanteOfFicha != null) {
                    oldHabitanteOfFicha.setFicha(null);
                    oldHabitanteOfFicha = em.merge(oldHabitanteOfFicha);
                }
                fichaNew.setHabitante(habitante);
                fichaNew = em.merge(fichaNew);
            }
            if (peceraOld != null && !peceraOld.equals(peceraNew)) {
                peceraOld.getListaHabitantes().remove(habitante);
                peceraOld = em.merge(peceraOld);
            }
            if (peceraNew != null && !peceraNew.equals(peceraOld)) {
                peceraNew.getListaHabitantes().add(habitante);
                peceraNew = em.merge(peceraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = habitante.getIdHabitante();
                if (findHabitante(id) == null) {
                    throw new NonexistentEntityException("The habitante with id " + id + " no longer exists.");
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
            Habitante habitante;
            try {
                habitante = em.getReference(Habitante.class, id);
                habitante.getIdHabitante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitante with id " + id + " no longer exists.", enfe);
            }
            FichaMedica ficha = habitante.getFicha();
            if (ficha != null) {
                ficha.setHabitante(null);
                ficha = em.merge(ficha);
            }
            Pecera pecera = habitante.getPecera();
            if (pecera != null) {
                pecera.getListaHabitantes().remove(habitante);
                pecera = em.merge(pecera);
            }
            em.remove(habitante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Habitante> findHabitanteEntities() {
        return findHabitanteEntities(true, -1, -1);
    }

    public List<Habitante> findHabitanteEntities(int maxResults, int firstResult) {
        return findHabitanteEntities(false, maxResults, firstResult);
    }

    private List<Habitante> findHabitanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Habitante.class));
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

    public Habitante findHabitante(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Habitante.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Habitante> rt = cq.from(Habitante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

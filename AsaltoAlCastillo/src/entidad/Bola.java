package entidad;

import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.collision.shapes.*;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import entidad.Entidad;
import javax.media.j3d.*;
import javax.vecmath.*;
import main.Juego;

public class Bola extends Entidad {
    public static final float DISTANCIA_MINIMA_ACTIVA = 0.125f;
    public static final int NUMERO_FRAMES_COMPROBAR = 16;
    float radio, radioDeteccion;
    int framesComprobados;
    Vector3f posicion = new Vector3f();
    public Bola(
            float radio,
            float radioDetec,
            Vector3f fuerza,
            String textura,
            BranchGroup conjunto,
            Juego juego) {

        // Si se desea programar una clase Esfera, su constrctor tendr�a esta linea
        super(juego, conjunto);
        
        // Aplicar la fuerza inicial (�se llama velocidad? absurdo)
        this.radio = radio;
        this.radioDeteccion = radioDetec;
        this.velocidad_lineal = fuerza;
        
        // Creando una apariencia
        // Creacion de formas visuales y fisicas
        Appearance ap = new Appearance();
        Texture tex = new TextureLoader(textura, null).getTexture();
        ap.setTexture(tex);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        ap.setTextureAttributes(texAttr);

        Sphere figuraVisual = new Sphere(radio, Sphere.GENERATE_TEXTURE_COORDS, ap);
        SphereShape figuraFisica = new SphereShape(radio);
        ramaFisica = new CollisionObject();
        ramaFisica.setCollisionShape(figuraFisica);
        ramaVisible.addChild(desplazamiento);
        desplazamiento.addChild(figuraVisual);
        this.branchGroup = conjunto;
    }

        public void actualizar() {
            super.actualizar();
            Vector3f nuevaPos = new Vector3f();
            cuerpoRigido.getCenterOfMassPosition(nuevaPos);
            posicion.sub(nuevaPos);
            if(posicion.length()<DISTANCIA_MINIMA_ACTIVA){
                framesComprobados++;
                if(framesComprobados>=NUMERO_FRAMES_COMPROBAR){
                    /* Pseudoc�digo (todav�a no hemos implementado sistemas de HP)
                    
                        FOR EACH CHAR IN CHARACTERS:
                            IF DISTANCE (CHAR, NUEVAPOS) < RADIO_COMPROBAR:
                                DA�AR(CHAR)
                    */
                    marcarParaEliminar();
                }
            } else {
                framesComprobados = 0;
            }
            cuerpoRigido.getCenterOfMassPosition(posicion);
        }
    
}

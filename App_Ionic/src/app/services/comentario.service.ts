import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection } from 'angularfire2/firestore';
import { Observable } from 'rxjs';
import { map } from 'rxjs/Operators';
import { Comentario } from '../models/comentario';


@Injectable({
  providedIn: 'root'
})
export class ComentarioService {
  private comentariosCollection: AngularFirestoreCollection<Comentario>;
  private comentarios: Observable<Comentario[]>;

  constructor(db: AngularFirestore) {
    this.comentariosCollection = db.collection<Comentario>('Comentarios');
    this.comentarios = this.comentariosCollection.snapshotChanges().pipe(map(
      actions => {
        return actions.map(a => {
          const data = a.payload.doc.data();
          const idComentario = a.payload.doc.id;
          return {idComentario, ...data};
        });
      }
    ));
  }

  getComentario() {
    return this.comentarios;
  }
}
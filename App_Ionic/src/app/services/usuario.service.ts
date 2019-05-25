import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection } from 'angularfire2/firestore';
import { Observable } from 'rxjs';
import { map } from 'rxjs/Operators';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private usuariosCollection: AngularFirestoreCollection<Usuario>;
  private usuarios: Observable<Usuario[]>;

  constructor(db:AngularFirestore) {
    this.usuariosCollection = db.collection<Usuario>('usuario');
    this.usuarios = this.usuariosCollection.snapshotChanges().pipe(map(
      actions => {
        return actions.map(a => {
          const data = a.payload.doc.data();
          const idUsuario = a.payload.doc.id;
          return {idUsuario, ... data};
        });
      }
    ));
  }

  getUsuario() {
    return this.usuarios;
  }
}
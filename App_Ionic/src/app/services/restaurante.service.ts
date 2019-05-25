import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection } from 'angularfire2/firestore';
import { Observable } from 'rxjs';
import { map } from 'rxjs/Operators';
import { Restaurante } from '../models/restaurante';

@Injectable({
  providedIn: 'root'
})
export class RestauranteService {
  private restaurantesCollection: AngularFirestoreCollection<Restaurante>;
  private restaurantes: Observable<Restaurante[]>;

  constructor(db:AngularFirestore) {
    this.restaurantesCollection = db.collection<Restaurante>('Restaurante');
    this.restaurantes = this.restaurantesCollection.snapshotChanges().pipe(map(
      actions => {
        return actions.map(a => {
          const data = a.payload.doc.data();
          const id = a.payload.doc.id;
          return {id, ... data};
        });
      }
    ));
  }

  getRestaurante() {
    return this.restaurantes;
  }
}
import { Component, OnInit, ViewChild } from '@angular/core';
import chartJs from 'chart.js';

import { RestauranteService } from '../services/restaurante.service';
import { Restaurante } from '../models/restaurante';

import { UsuarioService } from '../services/usuario.service'
import { Usuario } from '../models/usuario';

import { ComentarioService } from '../services/comentario.service'
import { Comentario } from '../models/comentario';


@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  restaurantes: Restaurante[];
  usuarios: Usuario[];
  comentarios: Comentario[];

  // Variables Nº de comentarios Restaurante
  public numberComentMaf: number = 0;
  public numberComentGul: number = 0;
  public numberComentKon: number = 0;
  public numberComentBel: number = 0;
  public numberComentMia: number = 0;

  // Variable de suma
  public sumaMaf: number = 0;
  public sumaGul: number = 0;
  public sumaKon: number = 0;
  public sumabel: number = 0;
  public sumaMia: number = 0;

  // Variable del precio
  public precioMaf: number = 0;
  public precioGul: number = 0;
  public precioKon: number = 0;
  public preciobel: number = 0;
  public precioMia: number = 0;

  // Variables media restaurante
  public MediaMaf: number = 0;
  public MediaGul: number = 0;
  public MediaKon: number = 0;
  public Mediabel: number = 0;
  public MediaMia: number = 0;

  // Variable Precio medio
  PrecioMedMaf: number = 0;
  PrecioMedGul: number = 0;
  PrecioMedKon: number = 0;
  PrecioMedbel: number = 0;
  PrecioMedMia: number = 0;

  constructor(public restauranteService: RestauranteService, public usuarioService: UsuarioService, public comentarioService: ComentarioService) { }


  ngOnInit() {

    this.usuarioService.getUsuario().subscribe(usuario => {
      this.usuarios = usuario;
    });

    // Poblar las variables de numeros de comentarios y variable de suma
    this.comentarioService.getComentario().subscribe(comentario => {
      this.comentarios = comentario;
      this.comentarios.forEach(comentario => {
        if (comentario.Restaurante === "Mamma Mia") {
          this.numberComentMia++;
          this.sumaMia = parseInt(comentario.Puntua) + this.sumaMia;
          this.precioMia = parseInt(comentario.precioMedio) + this.precioMia;
        } else if ((comentario.Restaurante === "Taco Bell")) {
          this.numberComentBel++;
          this.sumabel = parseInt(comentario.Puntua) + this.sumabel;
          this.preciobel = parseInt(comentario.precioMedio) + this.preciobel;
        } else if ((comentario.Restaurante === "La Mafia")) {
          this.numberComentMaf++;
          this.sumaMaf = parseInt(comentario.Puntua) + this.sumaMaf;
          this.precioMaf = parseInt(comentario.precioMedio) + this.precioMaf;
        } else if ((comentario.Restaurante === "Gran Hon Kong")) {
          this.numberComentKon++;
          this.sumaKon = parseInt(comentario.Puntua) + this.sumaKon;
          this.precioKon = parseInt(comentario.precioMedio) + this.precioKon;
        } else if ((comentario.Restaurante === "Pura Gula")) {
          this.numberComentGul++;
          this.sumaGul = parseInt(comentario.Puntua) + this.sumaGul;
          this.precioGul = parseInt(comentario.precioMedio) + this.precioGul;
        }
      });

      //  Hacer la media de la puntuación de los restaurantes 
      this.MediaMia = this.sumaMia / this.numberComentMia;
      this.MediaGul = this.sumaGul / this.numberComentGul;
      this.MediaKon = this.sumaKon / this.numberComentKon;
      this.MediaMaf = this.sumaMaf / this.numberComentMaf;
      this.Mediabel = this.sumabel / this.numberComentBel;

      //  Hacer la media de la puntuación de los restaurantes 
      this.PrecioMedMia = this.precioMia / this.numberComentMia;
      this.PrecioMedGul = this.precioGul / this.numberComentGul;
      this.PrecioMedKon = this.precioKon / this.numberComentKon;
      this.PrecioMedMaf = this.precioMaf / this.numberComentMaf;
      this.PrecioMedbel = this.preciobel / this.numberComentBel;
    });

    this.restauranteService.getRestaurante().subscribe(restaurante => {
      this.restaurantes = restaurante;
    });
  }

  // Enlazar la grafica con el HTML
  @ViewChild('barCanvas') barCanvas;
  @ViewChild('barCanvas1') barCanvas1;
  @ViewChild('barCanvas2') barCanvas2;

  // Declarar variables
  barChart: any;
  barChart1: any;
  barChart2: any;




  ngAfterViewInit() {

    // Asignar cada grafica con la variable
    setTimeout(() => {
      this.barChart = this.getBarChart();
      this.barChart1 = this.getBarChart1();
      this.barChart2 = this.getBarChart2();
    }, 150)
  }

  // Declarar el tipo de grafica
  getChart(context, chartType, data, options?) {
    return new chartJs(context, {
      data,
      options,
      type: chartType
    })
  }

  // Generar una tabla con los datos apropiados
  getBarChart() {
    const data = {
      labels: ['La Mafia', 'Pura Gula', 'Gran Hon Kong', 'Taco Bell', 'Mamma Mia'],
      datasets: [{
        label: 'Nº comentarios por Restaurantes',
        data: [this.numberComentMaf, this.numberComentGul, this.numberComentKon, this.numberComentBel, this.numberComentMia],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)'
        ],
        borderWidth: 1
      }]
    };

    const options = {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
    return this.getChart(this.barCanvas.nativeElement, 'bar', data, options);
  }

  // Generar una tabla con los datos apropiados
  getBarChart1() {
    const data = {
      labels: ['La Mafia', 'Pura Gula', 'Gran Hon Kong', 'Taco Bell', 'Mamma Mia'],
      datasets: [{
        label: 'Puntuación media de restaurantes',
        data: [this.MediaMaf, this.MediaGul, this.MediaKon, this.Mediabel, this.MediaMia],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)'
        ],
        borderWidth: 1
      }]
    };

    const options = {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
    return this.getChart(this.barCanvas1.nativeElement, 'bar', data, options);
  }

  // Generar una tabla con los datos apropiados
  getBarChart2() {
    const data = {
      labels: ['La Mafia', 'Pura Gula', 'Gran Hon Kong', 'Taco Bell', 'Mamma Mia'],
      datasets: [{
        label: 'Precio medio',
        data: [this.PrecioMedMaf, this.PrecioMedGul, this.PrecioMedKon, this.PrecioMedbel, this.PrecioMedMia],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)'
        ],
        borderWidth: 1
      }]
    };

    const options = {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
    return this.getChart(this.barCanvas2.nativeElement, 'bar', data, options);
  }
}

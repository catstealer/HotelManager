import {Component, OnInit} from '@angular/core';
import {StatisticsService} from '../../service/statistics.service';
import {Statistics} from '../../entities/statistics';
import {ChartDataSets, ChartOptions, ChartType} from 'chart.js';
import {Label} from 'ng2-charts';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  public barChartOptions: ChartOptions = {
    responsive: true,
    scales: {xAxes: [{}], yAxes: [{}]},
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };

  public moneySum = 0;
  public billsCount = 0;

  public barChartLabels: Label[] = [];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartPlugins = [pluginDataLabels];

  public moneyStats: ChartDataSets[] = [
    {data: [], label: 'Liczba rezerwacji'},
  ];

  public countStats: ChartDataSets[] = [
    {data: [], label: 'Kwota wydana przez rezerwujących'},
  ];

  constructor(private statisticsService: StatisticsService) {
  }

  ngOnInit(): void {
    this.statisticsService.getStatistics()
      .subscribe(val => this.patchStatisticsValue(val));
  }

  patchStatisticsValue(stats: Statistics) {
    this.moneyStats = [
      {data: stats.numberOfBillsEachDay, label: 'Liczba rezerwacji'}
    ];

    this.countStats = [
      {data: stats.totalMoneySpentEachDay, label: 'Kwota wydana przez rezerwujących'},
    ];

    this.moneySum = stats.totalMoneySpent;
    this.billsCount = stats.totalNumberOfBills;

    this.barChartLabels = stats.dates.map(this.convertDateToMonthString);
  }

  convertDateToMonthString(date: string): string {
    const listElement = date.split('-');
    const day = listElement[1];
    const month = listElement[2];
    return day + '.' + month;
  }

}

import {Component, inject, OnInit, signal} from '@angular/core';
import {StaCo} from '../../model/sta-co';
import {AppService} from '../../services/app.service';
import {StaCoDynamodbService} from '../../services/sta-co-dynamodb.service';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-search-dynamo',
  standalone: true,
  templateUrl: './search-dynamo.component.html',
  imports: [
    MatCardTitle,
    MatCard,
    FormsModule
  ],
  styleUrls: ['./search-dynamo.component.scss']
})
export class SearchDynamoComponent implements OnInit {
  private readonly staCoDynamodbService = inject(StaCoDynamodbService);
  readonly appService = inject(AppService);

  title = 'stamps-and-coins-manager';
  tableSizes = [5, 10, 15, 20];

  readonly allStaCos = signal<StaCo[]>([]);
  readonly selectedRecord = signal<StaCo | null>(null);
  readonly currentPageStaCo = signal(0);
  readonly currentPageStaCoSize = signal(5);
  readonly pagesStaCos = signal<number[]>([]);
  readonly sortColumn = signal('description');
  readonly order = signal('asc');

  ngOnInit(): void {
    this.fetchData();
  }

  refresh() {
    this.fetchData();
  }

  private fetchData() {
    this.staCoDynamodbService.searchUnfilteredDynamoDb(
      this.currentPageStaCo() + 1,
      this.currentPageStaCoSize())
      .subscribe(data => this.processResponse(data))
  }

  private processResponse(data: StaCo[]) {
    this.appService.token.set('authenticated');
    this.allStaCos.set(data);
    if (this.allStaCos().length == 0 && this.currentPageStaCo() > 1) {
      this.currentPageStaCo.set(0);
      this.fetchData()
    }

    this.staCoDynamodbService.countRecordsDynamoDb()
      .subscribe(data => {
        const totalPages = data / this.currentPageStaCoSize();
        const pages: number[] = [];
        for (let i = 0; i < totalPages; i++) {
          pages.push(i + 1);
        }
        this.pagesStaCos.set(pages);
      })

  }

  onTableSizeChange(event): void {
    this.currentPageStaCo.set(0);
    this.currentPageStaCoSize.set(event.target.value);
    this.fetchData();
  }

  setSelected(staCo: StaCo) {
    this.selectedRecord.set(staCo);
  }

  setEntityPage(page: number) {
    this.currentPageStaCo.set(page);
    this.fetchData();
  }

  setSortColumn(sortColumn: string) {
    if (sortColumn === this.sortColumn()) {
      this.order.set(this.order() === 'asc' ? 'desc' : 'asc');
    }
    this.sortColumn.set(sortColumn);
    this.fetchData();
  }

}

import {Component, OnInit} from "@angular/core";
import {StaCo} from "../../model/staCo";
import {AppService} from "../../services/app.service";
import {StaCoDynamodbService} from "../../services/sta.co.dynamodb.service";

@Component({
  selector: 'search-dynamo-component',
  templateUrl: './search.dynamo.component.html',
  styleUrls: ['./search.dynamo.component.scss']
})
export class SearchDynamoComponent implements OnInit {
  title = 'stamps-and-coins-manager';
  allStaCos: StaCo[];
  tableSizeEntities = 5;
  tableSizes = [5, 10, 15, 20];
  selectedRecord: StaCo;
  validationSize: boolean = true;
  validationContent: boolean = true;
  currentPageStaCo: number = 0;
  currentPageStaCoSize: number = 5;
  pagesStaCos: number[] = [];
  sortColumn: string = 'description';
  order: string = 'asc';
  private totalPages: number;

  constructor(private staCoDynamodbService: StaCoDynamodbService, public appService: AppService) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  refresh() {
    this.fetchData();
  }

  private fetchData() {
    this.staCoDynamodbService.searchUnfilteredDynamoDb(
      this.currentPageStaCo + 1,
      this.currentPageStaCoSize)
      .subscribe(data => this.processResponse(data))
  }

  private processResponse(data: StaCo[]) {
    this.appService.token = "authenticated";
    this.allStaCos = data;
    if (this.allStaCos.length == 0 && this.currentPageStaCo > 1) {
      this.currentPageStaCo = 0
      this.fetchData()
    }

    this.staCoDynamodbService.countRecordsDynamoDb()
      .subscribe(data => {
        this.totalPages = data / this.currentPageStaCoSize
        this.pagesStaCos = [];
        for (let i = 0; i < this.totalPages; i++) {
          this.pagesStaCos.push(i + 1);
        }
      })

  }

  onTableSizeChange(event): void {
    this.currentPageStaCo = 0;
    this.currentPageStaCoSize = event.target.value;
    this.fetchData();
  }

  setSelected(staCo: StaCo) {
    this.selectedRecord = staCo
  }

  setEntityPage(page: number) {
    this.currentPageStaCo = page;
    this.fetchData();
  }

  setSortColumn(sortColumn: string) {
    if (sortColumn === this.sortColumn) {
      this.order = this.order === "asc" ? "desc" : "asc";
    }
    this.sortColumn = sortColumn;
    this.fetchData();
  }

}

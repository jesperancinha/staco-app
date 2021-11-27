import {Component, OnInit} from "@angular/core";
import {StaCo} from "../../model/staCo";
import {StaCoService} from "../../services/sta.co.service";
import {AppService} from "../../services/app.service";
import {StacoResponse} from "../../model/staco.response";

@Component({
  selector: 'search-component',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  title = 'stamps-and-coins-manager';
  term: string = "";
  allStaCos: StaCo[];
  pageResults = 1;
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

  constructor(private staCoService: StaCoService, public appService: AppService) {
  }

  ngOnInit(): void {
    this.fetchData();
  }

  refresh() {
    this.validationSize = !(this.term && this.term.length > 10) || !this.term;
    this.validationContent = this.term && (/^[a-zA-Z0-9 ]*$/.test(this.term) || this.term.length === 0) || !this.term;
    if (this.validationSize && this.validationContent) {
      this.fetchData();
    }
  }

  private fetchData() {
    if (this.term) {
      this.staCoService.searchByTerm(
        this.term,
        this.currentPageStaCo,
        this.currentPageStaCoSize,
        this.sortColumn,
        this.order)
        .subscribe(data => this.processResponse(data))
    } else {
      this.staCoService.searchUnfiltered(
        this.currentPageStaCo,
        this.currentPageStaCoSize,
        this.sortColumn,
        this.order)
        .subscribe(data => this.processResponse(data))
    }
  }

  private processResponse(data: StacoResponse) {
    this.appService.token = "authenticated";
    this.allStaCos = data.staCoDtos;
    if (this.allStaCos.length < this.tableSizeEntities * (this.pageResults - 1)) {
      this.pageResults = 1;
    }
    this.pagesStaCos = [];
    for (let i = 0; i < data.totalPages; i++) {
      this.pagesStaCos.push(i + 1);
    }
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

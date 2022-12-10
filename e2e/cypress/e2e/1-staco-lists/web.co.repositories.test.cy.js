describe('Coroutines Repositories', () => {

    const host = Cypress.env('host') ? Cypress.env('host') : 'localhost';

    beforeEach(() => {
        cy.visit(`http://${host}:8080/login`)
    });

    function login() {
        cy.get('#username').type('admin');
        cy.get('#password').type('admin');
        cy.get('button.btn.btn-primary').contains('Login').click();

    }


    it('shows swagger', () => {
        cy.visit(`http://${host}:8080/api/staco/service/webjars/swagger-ui/index.html`);
        cy.get('h2').contains('OpenAPI definition').should('not.be.null');
        cy.wait(1000);

        cy.get('div[class="servers"] > label > select > option').should('have.value', 'http://localhost:8080/api/staco/service')
    });

    it('logs into coroutines page with user admin', () => {
        login();
    });

    it('clicks through pages 1 to 10 in coroutines page', () => {
        login();
        cy.wait(2000);
        cy.get('p').contains(/^1$/).click();
        cy.get('p').contains(/^2$/).click();
        cy.get('p').contains(/^3$/).click();
        cy.get('p').contains(/^4$/).click();
        cy.get('p').contains(/^5$/).click();
    });
})

- [ ] generer salaire
    - [ ] affichage
        - [ ] formulaire (
            select employee,
            mois debut ,
            annee debut,
            mois fin,
            annee fin,
            montant salaire de base,
        )
    - [ ] metier
        - [x] creation d'un salary structure assignement
            - [x] start date
            - [x] recuperation des donnees (currency et company a partir de l'employee)
            - [x] envoie de la requete
            - [x] boucler pour les mois suivants
            - [ ] traitement mois 12
- [ ] update salaire
    - [ ] affichage
        - [ ] formulaire
            {
                condition de salaire,
                select component
                    select > ou < ,
                    champ montant
                type de modification,
                pourcentage,
                list des employees,
            }
    - [ ]metier
        - [x] recuperer la liste des components
        - [ ] cree un model pour recuperer les donnees
        - [ ] pour chaque employees
            - [ ] generate salary slip for preview
            - [ ] recuperer le salaire base (SB)
            - [ ] verifier la condition
            - [ ] ajouter dans une liste d'employee
            - [ ] recuperer l'assignement pour la date de modification
            - [ ] cancel l'assignement a modifier
            - [ ] genere une salary structure avec les changement a partir de l'ancien
            - [ ] genere une nouvelle salary assignement avec cette structure a la date de l'ancien

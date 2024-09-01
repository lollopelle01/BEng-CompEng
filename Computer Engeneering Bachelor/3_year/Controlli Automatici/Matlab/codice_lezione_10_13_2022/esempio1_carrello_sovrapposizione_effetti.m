%
% Esempio Carrello: principio di sovrapposizione degli effetti
%
close all; clear all; clc;

% parametri fisici del sistema
mass = 0.5; % kg
elas = 1;   % costante elastica [N/m]

% condizione iniziale del carrello
pos_init = 0; % [m]
vel_init = 1; % [m/s]

% input applicato: sinusoidale con periodo 5 secondi
inp = @(t) sin(2.*pi.*t./5);

% intervallo di tempo
interv = 0:0.1:10; % da 0 a 10 secondi con passo 0.1

%% creazione oggetto sistema e soluzione equazione differenziale

% matrici del sistema
A = [0 1; -elas/mass 0];
B = [0; 1/mass];
C = [1 0];
D = 0;

% state-space model
modello = ss(A, B, C, D);

% evoluzione libera
uu_free = zeros(size(interv));
x0_free = [pos_init; vel_init];
[YY_free, TT_free, XX_free] = lsim(modello, uu_free, interv, x0_free);

% evoluzione forzata
uu_forced = inp(interv);
x0_forced = zeros(2, 1);
[YY_forced, TT_forced, XX_forced] = lsim(modello, uu_forced, interv, x0_forced);

% evoluzione totale
[YY_full, TT_full, XX_full] = lsim(modello, uu_forced, interv, x0_free);

%% plot

% prima componente dello stato: posizione
figure;
plot(TT_full, XX_free(:,1) + XX_forced(:,1));
hold on; grid on; zoom on; box on;
title('Traiettoria di posizione del carrello')
xlim([0, 10])
xlabel('tempo [s]')
ylabel('stato')
plot(TT_full, XX_full(:,1));
legend('libera + forzata', 'totale')

% seconda componente dello stato: velocità
figure;
plot(TT_full, XX_free(:,2) + XX_forced(:,2));
hold on; grid on; zoom on; box on;
title('Traiettoria di velocità del carrello')
xlim([0, 10])
xlabel('tempo [s]')
ylabel('stato')
plot(TT_full, XX_full(:,2));
legend('libera + forzata', 'totale')

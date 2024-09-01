%
% Diagramma di bode e risposta ad ingresso sinusoidale per un sistema complesso
%
close all; clear all; clc;

% funzione di trasferimento
s = tf('s');
G = 50*(5-s)^2/(s*(s^2+3*s+900)*(10*s+5));

% parametri della sinusoide in ingresso
omega = 0.1;    % pulsazione: 0.1 rad/s
tt = 0:0.01:500; % intervallo temporale: da 0 a 500 con passo 0.01

%% simulazione

risp_freq = evalfr(G, 1j*omega); % risposta in frequenza G(s) valutata per s = j*omega

fprintf('Pulsazione sinusoide in ingresso: %.2f rad/s (%.2f Hz)\n', omega, omega/(2*pi));
fprintf('Guadagno attraverso sistema: %.2f dB (cioè fattore moltiplicativo: %.2f)\n', 20*log10(abs(risp_freq)), abs(risp_freq));

% definizione dell'ingresso
uu = cos(omega*tt);

% simulazione sistema
yy = lsim(G, uu, tt);

%% plot

% diagramma di bode
figure;
bode(G);
box on; zoom on; grid on;

% plot ingresso-uscita
figure;
hold on; box on; zoom on; grid on;
plot(tt,uu,'LineWidth',1,'DisplayName','u(t)');
plot(tt,yy,'LineWidth',1,'DisplayName','y(t)');
xlabel('t');
legend;
